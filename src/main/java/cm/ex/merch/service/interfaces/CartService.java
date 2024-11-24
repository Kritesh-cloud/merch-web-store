package cm.ex.merch.service.interfaces;

import cm.ex.merch.dto.response.product.cart.BasicCartResponse;
import cm.ex.merch.dto.response.product.cart.CartListResponse;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface CartService {

    // CREATE or add
    public BasicCartResponse addToCart(String productId) throws IOException;

    // READ or list
    public CartListResponse listProductsInCart () throws AccessDeniedException;

    //UPDATE
    public BasicCartResponse UpdateProductQuantity(String productId, boolean increase) throws AccessDeniedException;

    // Delete or remove
    public BasicCartResponse removeFromCart (String productId) throws AccessDeniedException;

}

/*

#CREATE
BasicCartResponse : addToCart (String productId)

#READ
CartListResponse : listProductsInCart (String type)

#UPDATE
BasicCartResponse : UpdateProductQuantity (ring productId, boolean increase)

#DELETE
BasicCartResponse : removeFromCart (String productId)

*/
