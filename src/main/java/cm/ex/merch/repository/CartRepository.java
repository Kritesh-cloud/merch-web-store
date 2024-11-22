package cm.ex.merch.repository;

import cm.ex.merch.entity.Product;
import cm.ex.merch.entity.product.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM prod_cart pc WHERE pc.customer_id = UNHEX(REPLACE(:userId, '-', '')) LIMIT 1")
    Cart findCartByUserId(String userId);

}
