package cm.ex.merch.controller;

import cm.ex.merch.dto.request.AddProductDto;
import cm.ex.merch.dto.request.FilterProductDto;
import cm.ex.merch.dto.request.SignUpUserDto;
import cm.ex.merch.dto.response.product.BasicProductResponse;
import cm.ex.merch.dto.response.product.ProductListResponse;
import cm.ex.merch.dto.response.user.BasicUserResponse;
import cm.ex.merch.repository.ProductRepository;
import cm.ex.merch.service.ProductServiceImplement;
import cm.ex.merch.service.interfaces.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("product")
@RestController
public class ProductController {

    @Autowired
    private ProductServiceImplement productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/add")
    public ResponseEntity<BasicProductResponse> addProduct(@RequestBody @Valid AddProductDto addProductDto) {
        logger.info("#[INFO] ProductController - add. AddProductDto : {}", addProductDto.toString());
        return ResponseEntity.ok(productService.addProduct(addProductDto));
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
}
