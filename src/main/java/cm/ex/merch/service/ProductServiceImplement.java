package cm.ex.merch.service;

import cm.ex.merch.dto.request.AddProductDto;
import cm.ex.merch.dto.request.FilterProductDto;
import cm.ex.merch.dto.request.UpdateProductDto;
import cm.ex.merch.dto.response.product.BasicProductResponse;
import cm.ex.merch.dto.response.product.ProductDto;
import cm.ex.merch.dto.response.product.ProductListResponse;
import cm.ex.merch.entity.Product;
import cm.ex.merch.entity.image.Image;
import cm.ex.merch.entity.product.Category;
import cm.ex.merch.repository.CategoryRepository;
import cm.ex.merch.repository.ProductRepository;
import cm.ex.merch.service.interfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductServiceImplement implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ProductServiceImplement.class);


    @Override
    public BasicProductResponse addProduct(AddProductDto addProductDto) {

        BasicProductResponse basicProductResponse = new BasicProductResponse(false,null,null);

        Product product = modelMapper.map(addProductDto, Product.class);

        Category category = categoryRepository.findByName(addProductDto.getCategory());
        if(category == null){
            category = new Category("miscellaneous");
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setCategory(category);

        logger.info("#[INFO] ProductServiceImplement. addProductDto: {}",addProductDto.toString());
        logger.info("#[INFO] ProductServiceImplement. product: {}",product.toString());

        productRepository.save(product);
        basicProductResponse.setStatus(true);
        basicProductResponse.setMessage("New product added successfully");
        return basicProductResponse;
    }

    @Override
    public BasicProductResponse addProductWithImages(AddProductDto addProductDto, MultipartFile... files) throws IOException {
        return null;
    }

    @Override
    public ProductListResponse listProductBySerial(FilterProductDto filterProductDto) {

        ProductListResponse productListResponse = new ProductListResponse();
        productListResponse.setStatus(true);

        List<Product> productList = productRepository.findAll();

        if(productList.isEmpty()){
            productListResponse.setMessage("No product found");
            return productListResponse;
        }

        List<ProductDto> productDtoList = productList.stream()
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product,ProductDto.class);

                    productDto.setCategory(product.getCategory().getName());

                    productDto.setImageUrlList(imageSetToUrl(product.getImageList()));

                    return productDto;
                })
                .toList();
/*

List<User> usersList = userRepository.findAll();
        return usersList.stream()
                .map(user -> {
                    GeneralUserDto generalUser = modelMapper.map(user, GeneralUserDto.class);
                    Login login = loginRepository.findLoginById(user.getUserId());
                    generalUser.setRole(setAuthorityToListAuthority(user.getAuthorities()));
                    generalUser.setEmail(login.getEmail());
                    return generalUser;
                })
                .toList();
* */

//        productListResponse.setProductList(productList);

        return null;
    }

    @Override
    public BasicProductResponse updateProduct(UpdateProductDto updateProductDto) {
        return null;
    }

    @Override
    public BasicProductResponse updateProductWithImages(UpdateProductDto updateProductDto, MultipartFile... files) throws IOException {
        return null;
    }

    @Override
    public BasicProductResponse deleteProductById(UUID id) {
        return null;
    }

    @Override
    public BasicProductResponse deleteProductByIds(UUID[] ids) {
        return null;
    }


    private String[] imageSetToUrl(Set<Image> imageSet){
        return imageSet.stream()
                .map(
                        image -> {
                            UUID imageId = image.getId();

//                            TODO convert imageId to image Urls

                            return image.getName()+"png";
                        }
                )
                .toArray(String[]::new);
    }
}
