package cm.ex.merch.entity.newsletter;

import cm.ex.merch.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "newsletter")
public class Newsletter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter newsletter data")
    @Column(name = "subject")
    private String subject;

    @NotBlank(message="please enter newsletter data")
    @Column(name = "body")
    private String body;

    @Column(name = "schedule_send")
    private LocalDateTime scheduleSend;

    @NotBlank(message="please enter newsletter data")
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @NotBlank(message="please enter newsletter data")
    @Column(name = "status")
    private String status; // draft, scheduled, sent, canceled

    @Column(name = "imageUrl")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotBlank(message="please enter message")
    private User author;
}
