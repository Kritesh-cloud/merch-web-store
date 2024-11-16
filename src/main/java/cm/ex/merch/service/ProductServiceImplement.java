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
import cm.ex.merch.entity.user.Authority;
import cm.ex.merch.repository.AuthorityRepository;
import cm.ex.merch.repository.CategoryRepository;
import cm.ex.merch.repository.ImageRepository;
import cm.ex.merch.repository.ProductRepository;
import cm.ex.merch.security.authentication.UserAuth;
import cm.ex.merch.service.interfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
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
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied");

        List<GrantedAuthority> userAuthorityList = userAuth.getAuthority();
        if (userAuthorityList.contains(new SimpleGrantedAuthority("user")))
            throw new AccessDeniedException("Access denied");

        BasicProductResponse basicProductResponse = new BasicProductResponse(false, null, null);
        Product product = modelMapper.map(addProductDto, Product.class);
        Category category = categoryRepository.findByName(addProductDto.getCategory());
        if (category == null) category = new Category("miscellaneous");
        product.setCategory(category);
        logger.info("#[INFO] ProductServiceImplement. addProductDto: {}, product: {}", addProductDto.toString(),product.toString());

        productRepository.save(product);
        basicProductResponse.setStatus(true);
        basicProductResponse.setMessage("New product added successfully");
        return basicProductResponse;
    }

    @Override
    public BasicProductResponse addProductWithImage(AddProductDto addProductDto, MultipartFile... files) throws IOException {

        BasicProductResponse basicProductResponse = addProductOld(addProductDto);
        basicProductResponse.setRemarks(new String[]{"No remarks."});
        if (files.length == 0) return basicProductResponse;
        if (files.length == 1 && Objects.requireNonNull(files[0].getOriginalFilename()).isEmpty())
            return basicProductResponse;

        Product lastProduct = productRepository.findOneLastProduct();
        String[] remarks = {"Image(s) are added"};
        boolean firstImage = true;
        for (MultipartFile file : files) {
            Image image = new Image();
            String imgFile = Base64.getEncoder().encodeToString(file.getBytes());
            image.setImage(imgFile);
            image.setName(file.getOriginalFilename());
            image.setExtension(file.getContentType());
            image.setData(firstImage ? "prod-main" : "prod");
            image.setProduct(lastProduct);
            imageRepository.save(image);
            firstImage = false;
        }
        basicProductResponse.setRemarks(remarks);
        System.out.println("basicProductResponse: " + basicProductResponse.toString());
        return basicProductResponse;
    }

    @Override
    public ProductListResponse listProductBySerial(FilterProductDto filterProductDto) {

        ProductListResponse productListResponse = new ProductListResponse();
        productListResponse.setStatus(true);
        productListResponse.setMessage("No product found");
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) return productListResponse;

        List<ProductDto> productDtoList = productList.stream()
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);
                    productDto.setCategory(product.getCategory().getName());
                    List<Image> imageList = imageRepository.findImagesByProductId(product.getId().toString());

                    for (Image mainImage : imageList) {
                        if (mainImage.getData().equalsIgnoreCase("product-main")) {
                            productDto.setImageUrl("http://localhost:8081/image/" + mainImage.getId());
                        }
                    }
                    if (!imageList.isEmpty()) {
                        String[] imageUrls = imageListToUrl(imageList);
                        productDto.setImageUrlList(imageUrls);
                    }
                    return productDto;
                })
                .toList();

        productListResponse.setMessage("Product retrieved successfully");
        productListResponse.setProductList(productDtoList);
        return productListResponse;
    }

    @Override
    public byte[] getImageById(String imageId) {
        Image imageById = imageRepository.findImagesById(imageId);
        return java.util.Base64.getDecoder().decode(imageById.getImage());
    }

    @Override
    public BasicProductResponse updateProductOld(UpdateProductDto updateProductDto) throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied");

        Authority authority = authorityRepository.findByAuthority("user");
        GrantedAuthority authorityUser = new SimpleGrantedAuthority(authority.getAuthority());
        List<GrantedAuthority> userAuthorityList = userAuth.getAuthority();
        if (userAuthorityList.contains(authorityUser)) throw new AccessDeniedException("Access denied");

        BasicProductResponse basicProductResponse = new BasicProductResponse(false, null, null);
        Product oldProduct = productRepository.findProductById(updateProductDto.getId());
        if (oldProduct == null) throw new NoSuchElementException("Product not found");

        Product newProduct = modelMapper.map(updateProductDto, Product.class);
        newProduct.setCreatedAt(oldProduct.getCreatedAt());

        Category category = categoryRepository.findByName(updateProductDto.getCategory());
        category = (category == null) ? new Category("miscellaneous") : category;

        newProduct.setCategory(category);
        String[] remarks = new String[updateProductDto.getRemoveImageIds().length];
        int count = 1;
        for (String removeImageId : updateProductDto.getRemoveImageIds()) {
            Image removeImage = imageRepository.findImagesById(removeImageId);
            String remark = removeImage.getName() + "(" + (count) + ") image is removed.";
            remarks[count - 1] = remark;
            count++;
            imageRepository.delete(removeImage);
        }

        basicProductResponse.setRemarks(remarks);
        logger.info("#[INFO] ProductServiceImplement. updateProductDto: {}, product: {}", updateProductDto.toString(), newProduct.toString());
        productRepository.save(newProduct);
        basicProductResponse.setStatus(true);
        basicProductResponse.setMessage("Product updated successfully");
        return basicProductResponse;
    }

    @Override
    public BasicProductResponse updateProductWithImages(UpdateProductDto updateProductDto, MultipartFile... files) throws IOException {
        BasicProductResponse basicProductResponse = updateProductOld(updateProductDto);
        if (files.length == 0) return basicProductResponse;
        if (files.length == 1 && Objects.requireNonNull(files[0].getOriginalFilename()).isEmpty())
            return basicProductResponse;

        Product updatedProduct = productRepository.findProductById(updateProductDto.getId());
        String[] remarks = basicProductResponse.getRemarks();
        String[] newRemarks = Arrays.copyOf(remarks, remarks.length + 1);
        newRemarks[remarks.length] = "Image(s) are updated";
        boolean firstImage = true;
        for (MultipartFile file : files) {
            logger.info("#[INFO] ProductServiceImplement. file.getName(): {}", file.getName());
            Image image = new Image();
            String imgFile = Base64.getEncoder().encodeToString(file.getBytes());
            image.setImage(imgFile);
            image.setName(file.getOriginalFilename());
            image.setExtension(file.getContentType());
            image.setData(firstImage ? "product-main" : "product");
            image.setProduct(updatedProduct);

            logger.info("#[INFO] ProductServiceImplement. image.getName(): {}", image.getName());
            imageRepository.save(image);
            firstImage = false;
        }

        basicProductResponse.setRemarks(newRemarks);
        return basicProductResponse;
    }

    @Override
    public BasicProductResponse deleteProductById(String id) throws AccessDeniedException {

        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied");

        Authority authority = authorityRepository.findByAuthority("user");
        GrantedAuthority authorityUser = new SimpleGrantedAuthority(authority.getAuthority());
        List<GrantedAuthority> userAuthorityList = userAuth.getAuthority();
        if (userAuthorityList.contains(authorityUser)) throw new AccessDeniedException("Access denied");

        BasicProductResponse basicProductResponse = new BasicProductResponse(true, "Product(s) deleted successfully", null);
        Product deleteProduct = productRepository.findProductById(id);
        if (deleteProduct == null) throw new NoSuchElementException("Product not found");

        return basicProductResponse;
    }

    @Override
    public BasicProductResponse deleteProductByIds(String[] ids) throws AccessDeniedException {

        BasicProductResponse basicProductResponse = new BasicProductResponse(true, "Product(s) deleted successfully", null);
        String[] remarks = new String[ids.length];
        int count = 0;
        for (String id : ids) {
            remarks[count] = "Product id " + id + " deleted successfully.";
            BasicProductResponse deletedProductResponse = deleteProductById(id);
            count++;
        }
        basicProductResponse.setMessage(count + " product(s) deleted successfully.");
        basicProductResponse.setRemarks(remarks);
        return basicProductResponse;
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
