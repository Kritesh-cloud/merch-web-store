package cm.ex.merch.entity.product;

import cm.ex.merch.entity.Product;
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
@Table(name = "prod_review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "Please enter review data")
    @Column(name = "review")
    private String review;

    @Column(name = "priority", columnDefinition = "INT DEFAULT 3")
    private int priority = 3;  // Default priority in Java side

    @Column(name = "hidden", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean hidden = false; // Default hidden status in Java side

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public Review(String review, int priority, LocalDateTime createdAt, LocalDateTime updatedAt, User user, Product product) {
        this.review = review;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.product = product;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
