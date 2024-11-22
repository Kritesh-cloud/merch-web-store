package cm.ex.merch.repository;

import cm.ex.merch.entity.product.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, UUID> {

    @Query( nativeQuery = true, value = "SELECT * FROM prod_quantity pq WHERE pq.cart_id = UNHEX(REPLACE(:cartId, '-', ''))")
    List<ProductQuantity> findQuantityListByCartId(String cartId);
}
