package cm.ex.merch.service;

import cm.ex.merch.dto.request.product.AddProductDto;
import cm.ex.merch.dto.request.product.FilterProductDto;
import cm.ex.merch.dto.request.product.UpdateProductDto;
import cm.ex.merch.dto.response.product.BasicProductResponse;
import cm.ex.merch.dto.response.product.ProductDto;
import cm.ex.merch.dto.response.product.ProductListResponse;
import cm.ex.merch.entity.Product;
import cm.ex.merch.entity.image.Image;
import cm.ex.merch.entity.product.Category;
import cm.ex.merch.repository.*;
import cm.ex.merch.security.authentication.UserAuth;
import cm.ex.merch.service.interfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class ProductServiceImplement implements ProductService {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ProductServiceImplement.class);

    @Override
    public BasicProductResponse addProductOld(AddProductDto addProductDto) throws AccessDeniedException {

        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        if (userAuth.getAuthority().contains(new SimpleGrantedAuthority(authorityRepository.findByAuthority("user").getAuthority())))
            throw new AccessDeniedException("Access denied. Not authorized.");

        Product product = modelMapper.map(addProductDto, Product.class);
        Category category = categoryRepository.findByName(addProductDto.getCategory());
        product.setCategory(category != null ? category : new Category("miscellaneous"));

        productRepository.save(product);
        return new BasicProductResponse(true, "New product added successfully",new String[]{"no remarks"});
    }

    @Override
    public BasicProductResponse addProductWithImage(AddProductDto addProductDto, MultipartFile... files) throws IOException {

        BasicProductResponse basicProductResponse = addProductOld(addProductDto);
        if (files.length == 0 || (files.length == 1 && Objects.requireNonNull(files[0].getOriginalFilename()).isEmpty()))
            return basicProductResponse;

        Product lastProduct = productRepository.findOneLastProduct();
        boolean firstImage = true;
        for (MultipartFile file : files) {
            String imgFile = Base64.getEncoder().encodeToString(file.getBytes());
            Image image = new Image(imgFile,file.getOriginalFilename(),file.getContentType(),lastProduct.getName(),firstImage ? "product-main" : "product",lastProduct);
            imageRepository.save(image);
            firstImage = false;
        }

        basicProductResponse.setRemarks(new String[]{"Image(s) are added"});
        return basicProductResponse;
    }

    @Override
    public ProductListResponse listProductBySerial(FilterProductDto filterProductDto) {

        if (productRepository.findAll().isEmpty())
            return new ProductListResponse(false, "No product found", new ArrayList<>());

        List<ProductDto> productDtoList = productRepository.findAll().stream()
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);
                    productDto.setCategory(product.getCategory().getName());

                    List<Image> imageList = imageRepository.findImagesByProductId(product.getId().toString());
                    productDto.setImageUrlList(imageListToUrl(imageList));

                    imageList.stream()
                            .filter(image -> "product-main".equalsIgnoreCase(image.getData()))
                            .findFirst()
                            .ifPresent(mainImage -> productDto.setImageUrl("http://localhost:8081/image/" + mainImage.getId()));

                    return productDto;
                })
                .toList();

        return new ProductListResponse(true, "Product retrieved successfully", productDtoList);
    }

    @Override
    public byte[] getImageById(String imageId) {
        Image imageById = imageRepository.findImagesById(imageId);

        if(imageById == null) throw new NoSuchElementException("Image not found");

        return Base64.getDecoder().decode(imageById.getImage());
    }

    @Override
    public BasicProductResponse updateProductOld(UpdateProductDto updateProductDto) throws AccessDeniedException {

        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        if (userAuth.getAuthority().contains(new SimpleGrantedAuthority(authorityRepository.findByAuthority("user").getAuthority())))
            throw new AccessDeniedException("Access denied. Not authorized.");

        Product oldProduct = productRepository.findProductById(updateProductDto.getId());
        if (oldProduct == null) throw new NoSuchElementException("Product not found");

        Product product = modelMapper.map(updateProductDto, Product.class);
        product.setCreatedAt(oldProduct.getCreatedAt());
        Category category = categoryRepository.findByName(updateProductDto.getCategory());
        product.setCategory(category != null ? category : new Category("miscellaneous"));

        String[] remarks = new String[updateProductDto.getRemoveImageIds().length];
        int count = 1;
        for (String removeImageId : updateProductDto.getRemoveImageIds()) {
            Image removeImage = imageRepository.findImagesById(removeImageId);
            String remark = removeImage.getName() + "(" + (count) + ") image is removed.";
            remarks[count - 1] = remark;
            count++;
            imageRepository.delete(removeImage);
        }

        productRepository.save(product);
        return new BasicProductResponse(true, "Product updated successfully",remarks);
    }

    @Override
    public BasicProductResponse updateProductWithImages(UpdateProductDto updateProductDto, MultipartFile... files) throws IOException {

        BasicProductResponse basicProductResponse = updateProductOld(updateProductDto);
        if (files.length == 0 || (files.length == 1 && Objects.requireNonNull(files[0].getOriginalFilename()).isEmpty()))
            return basicProductResponse;

        Product updatedProduct = productRepository.findProductById(updateProductDto.getId());
        String[] remarks = basicProductResponse.getRemarks();
        String[] newRemarks = Arrays.copyOf(remarks, remarks.length + 1);
        newRemarks[remarks.length] = "Image(s) are updated";
        boolean firstImage = true;
        for (MultipartFile file : files) {
            String imgFile = Base64.getEncoder().encodeToString(file.getBytes());
            Image image = new Image(imgFile,file.getOriginalFilename(),file.getContentType(),updatedProduct.getName(),firstImage ? "product-main" : "product",updatedProduct);
            imageRepository.save(image);
            firstImage = false;
        }

        basicProductResponse.setRemarks(newRemarks);
        return basicProductResponse;
    }

    @Override
    public BasicProductResponse deleteProductById(String id) throws AccessDeniedException {

        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        if (userAuth.getAuthority().contains(new SimpleGrantedAuthority(authorityRepository.findByAuthority("user").getAuthority())))
            throw new AccessDeniedException("Access denied. Not authorized.");

        Product deleteProduct = productRepository.findProductById(id);
        if (deleteProduct == null) throw new NoSuchElementException("Product not found");

        productRepository.delete(deleteProduct);
        return new BasicProductResponse(true, "Product(s) deleted successfully", new String[]{"Product is deleted successfully"});
    }

    @Override
    public BasicProductResponse deleteProductByIds(String[] ids) throws AccessDeniedException {

        String[] remarks = new String[ids.length];
        int count = 0;
        for (String id : ids) {
            remarks[count] = "Product id " + id + " deleted successfully.";
            deleteProductById(id);
            count++;
        }

        return new BasicProductResponse(true,count + " product(s) deleted successfully.",remarks);
    }

    private String[] imageListToUrl(List<Image> imageList) {
        return imageList.stream()
                .map(
                        image -> {
                            UUID imageId = image.getId();
                            String path = "http://localhost:8081/";
                            return path + "image/" + imageId;
                        }
                )
                .toArray(String[]::new);
    }

}
