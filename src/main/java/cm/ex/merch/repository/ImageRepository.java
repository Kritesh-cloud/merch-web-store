package cm.ex.merch.repository;

import cm.ex.merch.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

        @Query(nativeQuery = true, value="SELECT * FROM images i where i.article_id = :id")
        List<Image> findImagesByProductId(Long id);
}
