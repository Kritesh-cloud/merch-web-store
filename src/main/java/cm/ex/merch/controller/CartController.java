package cm.ex.merch.controller;

import cm.ex.merch.dto.response.product.cart.BasicCartResponse;
import cm.ex.merch.dto.response.product.cart.CartListResponse;
import cm.ex.merch.service.CartServiceImplement;
import cm.ex.merch.service.interfaces.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RequestMapping("/cart")
@RestController
public class CartController {

    final CartServiceImplement cartServiceImplement;

    public CartController(CartServiceImplement cartServiceImplement) {
        this.cartServiceImplement = cartServiceImplement;
    }

    @PostMapping("/add-product")
    public ResponseEntity<BasicCartResponse> addToCart(@PathVariable String productId) throws IOException {
        return new ResponseEntity<BasicCartResponse>(cartServiceImplement.addToCart(productId), HttpStatus.OK);
    }

    @GetMapping("/list-product")
    public ResponseEntity<CartListResponse> listProductsInCart() throws AccessDeniedException {
        return new ResponseEntity<CartListResponse>(cartServiceImplement.listProductsInCart(), HttpStatus.OK);
    }

    @PostMapping("/update-quantity")
//    POST /update-quantity?productId=12345&increase=true
    public ResponseEntity<BasicCartResponse> UpdateProductQuantity(@RequestParam String productId, @RequestParam boolean increase) throws AccessDeniedException {
        return new ResponseEntity<BasicCartResponse>(cartServiceImplement.UpdateProductQuantity(productId,increase), HttpStatus.OK);
    }

    @PostMapping("/remove-product")
    public ResponseEntity<BasicCartResponse> removeFromCart(@PathVariable String productId) throws AccessDeniedException {
        return new ResponseEntity<BasicCartResponse>(cartServiceImplement.removeFromCart(productId), HttpStatus.OK);
    }
}
