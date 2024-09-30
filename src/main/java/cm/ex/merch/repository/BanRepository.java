package cm.ex.merch.repository;

import cm.ex.merch.entity.user.Ban;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface BanRepository extends JpaRepository<Ban, UUID> {

    //@Query(nativeQuery = true, value = "SELECT * FROM user_ban ub WHERE ub.user_id = :userId");
    Optional<Ban> findByUserId(UUID userId);
}
