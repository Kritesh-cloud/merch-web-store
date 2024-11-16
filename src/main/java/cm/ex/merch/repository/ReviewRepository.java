package cm.ex.merch.repository;

import cm.ex.merch.entity.product.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM prod_review p WHERE p.id = UNHEX(REPLACE(:id, '-', '')) LIMIT 1")
    Review findReviewById(String id);

//    SELECT * FROM prod_review p WHERE p.product_id = UNHEX(REPLACE(:productId, '-', '')) AND p.user_id = UNHEX(REPLACE(:userId, '-', ''))
//    SELECT created_at FROM prod_review ORDER BY created_at DESC
    @Query(nativeQuery = true, value = "SELECT * FROM prod_review p WHERE p.product_id = UNHEX(REPLACE(:productId, '-', '')) AND p.user_id = UNHEX(REPLACE(:userId, '-', '')) ORDER BY updated_at DESC LIMIT 1")
    Review findReviewByProductAndUserIds(String productId, String userId);

    @Query(nativeQuery = true, value = "SELECT * FROM prod_review p WHERE p.user_id = UNHEX(REPLACE(:userId, '-', '')) LIMIT 1")
    Review findReviewByUserId(String userId);

    @Query(nativeQuery = true, value = "SELECT id, review, priority FROM prod_review ORDER BY priority ASC LIMIT :start, :count")
    List<Review> findReviewByPriority(int start, int count);

}

// SELECT id, review, priority FROM prod_review ORDER BY priority ASC;


// DESC LIMIT 1