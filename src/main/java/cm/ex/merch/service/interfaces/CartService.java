package cm.ex.merch.service.interfaces;

import cm.ex.merch.dto.response.product.cart.BasicCartResponse;
import cm.ex.merch.dto.response.product.cart.CartListResponse;

import java.io.IOException;

public interface CartService {

    // CREATE or add
    public BasicCartResponse addToCart(String productId, String type) throws IOException;

    // READ or list
    public CartListResponse listProductsInCart ();

    // Delete or remove
    public BasicCartResponse removeFromCart (String productId, String type);

}

/*

#CREATE
BasicCartAndWishlistResponse : addToCartOrWishlist (String productId, String type)

#READ
CartAndWishlistResponse : listProductsInCartOrWishlist (String type)

#UPDATE

#DELETE
BasicCartAndWishlistResponse : removeFromCartOrWishlist (String productId, String type)

*/
