package cm.ex.merch.service.interfaces;

import cm.ex.merch.dto.request.product.AddProductDto;
import cm.ex.merch.dto.request.product.FilterProductDto;
import cm.ex.merch.dto.request.product.UpdateProductDto;
import cm.ex.merch.dto.response.product.BasicProductResponse;
import cm.ex.merch.dto.response.product.ProductListResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface ProductService {

    //CREATE
    public BasicProductResponse addProductOld(AddProductDto addProductDto) throws AccessDeniedException;
    public BasicProductResponse addProductWithImage(AddProductDto addProductDto, MultipartFile... files) throws IOException;

    //READ
    public ProductListResponse listProductBySerial(FilterProductDto filterProductDto);
    public byte[] getImageById(String imageId);

    //UPDATE
    public BasicProductResponse updateProductOld(UpdateProductDto updateProductDto) throws AccessDeniedException;
    public BasicProductResponse updateProductWithImages(UpdateProductDto updateProductDto, MultipartFile... files) throws IOException;

    //DELETE
    public BasicProductResponse deleteProductById(String id) throws AccessDeniedException;
    public BasicProductResponse deleteProductByIds(String[] ids) throws AccessDeniedException;
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