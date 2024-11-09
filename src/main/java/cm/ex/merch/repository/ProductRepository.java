package cm.ex.merch.repository;

import cm.ex.merch.entity.Product;
import cm.ex.merch.entity.User;
import cm.ex.merch.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByName(String name);

    Product findByGameTitle(String gameTitle);

    Product findByBrand(String brand);

    Product findByCategory(Category category);


}
/*

find by all
find by name
find by brand
find by game title
find by category

* */
