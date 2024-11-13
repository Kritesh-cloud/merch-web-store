package cm.ex.merch.repository;

import cm.ex.merch.entity.Product;
import cm.ex.merch.entity.product.Category;
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

    @Query(nativeQuery = true, value = "SELECT * FROM products ORDER BY created_at DESC")
    Product findLastProduct();

    @Query(nativeQuery = true, value = "SELECT * FROM products ORDER BY created_at DESC LIMIT 1")
    Product findOneLastProduct();

    //	select*from users order by id desc limit 1;
//    @Query(nativeQuery = true, value="SELECT * FROM products ORDER BY article_id DESC LIMIT 1")
//    Product findLastArticle();
}
/*

find by all
find by name
find by brand
find by game title
find by category

* */
