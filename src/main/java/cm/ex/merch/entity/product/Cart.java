package cm.ex.merch.entity.product;

import cm.ex.merch.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prod_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "purchased_at")
    private LocalDateTime purchasedAt;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private List<ProductQuantity> productQuantity;

    public Cart(User customer, List<ProductQuantity> productQuantity) {
        this.customer = customer;
        this.productQuantity = productQuantity;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
