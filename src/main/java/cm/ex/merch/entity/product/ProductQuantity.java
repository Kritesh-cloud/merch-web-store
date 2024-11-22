package cm.ex.merch.entity.product;

import cm.ex.merch.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prod_quantity")
public class ProductQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "quantity", columnDefinition = "INT DEFAULT 1")
    private int quantity;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public ProductQuantity(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }
}
