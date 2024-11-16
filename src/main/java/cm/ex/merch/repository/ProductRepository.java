package cm.ex.merch.repository;

import cm.ex.merch.entity.Product;
import cm.ex.merch.entity.product.Category;
import cm.ex.merch.entity.product.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByName(String name);

    Product findByGameTitle(String gameTitle);

    Product findByBrand(String brand);

    Product findByCategory(Category category);

    @Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.id = UNHEX(REPLACE(:id, '-', '')) LIMIT 1")
    Product findProductById(String id);

    @Query(nativeQuery = true, value = "SELECT * FROM products ORDER BY created_at DESC LIMIT 1")
    Product findOneLastProduct();

    @Query(nativeQuery = true, value = "SELECT * FROM products LIMIT :start, :count")
    List<Product> findProductByLimit(int start, int count);

}
/*

find by all
find by name
find by brand
find by game title
find by category

* */
