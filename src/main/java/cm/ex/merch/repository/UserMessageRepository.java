package cm.ex.merch.repository;

import cm.ex.merch.entity.user.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserMessageRepository extends JpaRepository<Message, UUID> {
}
