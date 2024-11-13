package cm.ex.merch.repository;

import cm.ex.merch.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

        //SELECT * FROM products WHERE id = UNHEX(REPLACE('05b7dd8d-3dae-4016-974e-9b9fe4a03957', '-', ''));
        //SELECT * FROM images i WHERE i.product_id = UNHEX(REPLACE('54b7a014-d0ad-47d1-877c-b2f3670f0018', '-', '')) LIMIT 1;
        @Query(nativeQuery = true, value = "SELECT * FROM images i WHERE i.product_id = UNHEX(REPLACE(:id, '-', ''))")
        List<Image> findImagesByProductId(String id);

        @Query(nativeQuery = true, value = "SELECT * FROM images i WHERE i.id = UNHEX(REPLACE(:id, '-', '')) LIMIT 1")
        Image findImagesById(String id);

        @Query(nativeQuery = true, value = "SELECT * FROM images ORDER BY created_at DESC LIMIT 1")
        Image findLastImage();
}
