package cm.ex.merch.repository;

import cm.ex.merch.entity.user.Ban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BanRepository extends JpaRepository<Ban, UUID> {

    //@Query(nativeQuery = true, value = "SELECT * FROM user_ban ub WHERE ub.user_id = :userId LIMIT 1");
    @Query(nativeQuery = true, value = "SELECT * FROM user_ban ub WHERE ub.user_id = UNHEX(REPLACE(:userId, '-', '')) LIMIT 1")
    Ban findBannedUserByUserId(String userId);
}
