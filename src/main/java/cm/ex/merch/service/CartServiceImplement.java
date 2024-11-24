package cm.ex.merch.service;

import cm.ex.merch.dto.response.product.cart.BasicCartResponse;
import cm.ex.merch.dto.response.product.cart.CartListResponse;
import cm.ex.merch.entity.Product;
import cm.ex.merch.entity.User;
import cm.ex.merch.entity.product.Cart;
import cm.ex.merch.entity.product.ProductQuantity;
import cm.ex.merch.repository.*;
import cm.ex.merch.security.authentication.UserAuth;
import cm.ex.merch.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class CartServiceImplement implements CartService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public BasicCartResponse addToCart(String productId) throws IOException {

        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        Product product = productRepository.findProductById(productId);
        if(product==null) throw new NoSuchElementException("Product not found");

        Cart cart = cartRepository.findCartByUserId(userAuth.getId());

        User user = userRepository.findUserByUserId(userAuth.getId());

        if(cart == null) cart = new Cart(user, List.of(new ProductQuantity(1, product)));

        cartRepository.save(cart);

        return new BasicCartResponse(true, "Product added to the cart",new String[]{"no remarks"});
    }

    @Override
    public CartListResponse listProductsInCart() throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        Cart cart = cartRepository.findCartByUserId(userAuth.getId());

        if(cart == null) return new CartListResponse(true,"No product in cart","", new ArrayList<>());

        return new CartListResponse(true,"Product list in cart","", cart.getProductQuantity());
    }

    @Override
    public BasicCartResponse UpdateProductQuantity(String productId, boolean increase) throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        Cart cart = cartRepository.findCartByUserId(userAuth.getId());

        List<ProductQuantity> productQuantityList = cart.getProductQuantity();

        List<ProductQuantity> newProductQuantityList = productQuantityList.stream()
                .flatMap(productQuantity -> {
                    Product cartProduct = productQuantity.getProduct();
                    int quantity = productQuantity.getQuantity();
                    Product product = productRepository.findProductById(productId);

                    if (!product.equals(cartProduct)) return Stream.of(productQuantity);

                    quantity = increase ? quantity + 1 : quantity - 1;
                    return quantity > 0 ? Stream.of(new ProductQuantity(quantity, product)) : Stream.empty();
                })
                .toList();

        cart.setProductQuantity(newProductQuantityList);
        cartRepository.save(cart);
        return new BasicCartResponse(true, "Product updated in cart successfully",new String[]{"no remarks"});
    }

    @Override
    public BasicCartResponse removeFromCart(String productId) throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied. Not authenticated.");

        Cart cart = cartRepository.findCartByUserId(userAuth.getId());

        List<ProductQuantity> productQuantityList = cart.getProductQuantity();

        List<ProductQuantity> newProductQuantityList = productQuantityList.stream()
                .flatMap(productQuantity -> {
                    Product cartProduct = productQuantity.getProduct();
                    Product product = productRepository.findProductById(productId);

                    if (product.equals(cartProduct)) return Stream.empty();
                    return Stream.of(productQuantity);
                })
                .toList();

        cart.setProductQuantity(newProductQuantityList);
        cartRepository.save(cart);
        return new BasicCartResponse(true, "Product removed from cart successfully",new String[]{"no remarks"});
    }
}
