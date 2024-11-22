package cm.ex.merch.repository;

import cm.ex.merch.entity.product.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {
}
