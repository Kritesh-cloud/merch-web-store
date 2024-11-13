package cm.ex.merch.controller;

import cm.ex.merch.dto.request.AddProductDto;
import cm.ex.merch.dto.request.FilterProductDto;
import cm.ex.merch.dto.request.UpdateProductDto;
import cm.ex.merch.dto.response.product.BasicProductResponse;
import cm.ex.merch.dto.response.product.ProductListResponse;
import cm.ex.merch.service.ProductServiceImplement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("product")
@RestController
public class ProductController {

    @Autowired
    private ProductServiceImplement productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

//    @PostMapping("/add")
//    public ResponseEntity<BasicProductResponse> addProduct(@RequestBody @Valid AddProductDto addProductDto) throws AccessDeniedException {
//        logger.info("#[INFO] ProductController - add. AddProductDto : {}", addProductDto.toString());
//        return ResponseEntity.ok(productService.addProduct(addProductDto));
//    }

//    @PreAuthorize("hasAnyAuthority('admin','editor','reader')")
    @PostMapping("/add")
    public ResponseEntity<BasicProductResponse> addProduct(@RequestPart("form") @Valid AddProductDto addProductDto, @RequestPart("img") MultipartFile... imageFiles) throws IOException {
        logger.info("#[INFO] ProductController - add. AddProductDto : {}", addProductDto.toString());
        logger.info("#[INFO] ProductController - add. imageFiles : {}", imageFiles.length);
        return new ResponseEntity<BasicProductResponse>(productService.addProductWithImage(addProductDto, imageFiles), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<BasicProductResponse> updateProduct(@RequestPart("form") @Valid UpdateProductDto updateProductDto, @RequestPart("img") MultipartFile... imageFiles) throws IOException {
        logger.info("#[INFO] ProductController - add. UpdateProductDto : {}", updateProductDto.toString());
        logger.info("#[INFO] ProductController - add. imageFiles : {}", imageFiles.length);
        return new ResponseEntity<BasicProductResponse>(productService.updateProductWithImages(updateProductDto, imageFiles), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ProductListResponse> listProduct(
            @RequestParam(value = "name", defaultValue="") String name,
            @RequestParam(value = "brand", defaultValue="") String brand,
            @RequestParam(value = "gameTitle", defaultValue="") String gameTitle,
            @RequestParam(value = "category", defaultValue="") String category,
            @RequestParam(value = "availability", defaultValue="both") String availability,
            @RequestParam(value = "pageNumber", defaultValue="0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue="0") int pageSize
    ) {
        FilterProductDto filterProductDto = new FilterProductDto();
        logger.info("#[INFO] ProductController - list. filterProductDto : {}", filterProductDto.toString());
        return ResponseEntity.ok(productService.listProductBySerial(filterProductDto));
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> getProductImageByUrl(@PathVariable String imageId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(productService.getImageById(imageId),headers,HttpStatus.OK);
    }
}
