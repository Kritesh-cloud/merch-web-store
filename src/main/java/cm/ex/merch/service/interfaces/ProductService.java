package cm.ex.merch.service.interfaces;

import cm.ex.merch.dto.request.AddProductDto;
import cm.ex.merch.dto.request.FilterProductDto;
import cm.ex.merch.dto.request.UpdateProductDto;
import cm.ex.merch.dto.response.product.BasicProductResponse;
import cm.ex.merch.dto.response.product.ProductListResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ProductService {

    //CREATE
    public BasicProductResponse addProduct(AddProductDto addProductDto);
    public BasicProductResponse addProductWithImages(AddProductDto addProductDto, MultipartFile... files) throws IOException;

    //READ
    public ProductListResponse listProductBySerial(FilterProductDto filterProductDto);

    //UPDATE
    public BasicProductResponse updateProduct(UpdateProductDto updateProductDto);
    public BasicProductResponse updateProductWithImages(UpdateProductDto updateProductDto, MultipartFile... files) throws IOException;

    //DELETE
    public BasicProductResponse deleteProductById(UUID id);
    public BasicProductResponse deleteProductByIds(UUID[] ids);
}


/*

create
add product
add product with images

read
list all product
list by name
list by brand
list by game title
list by category

update
update product
update product with images


delete
delete product by id
delete product by ids


* */