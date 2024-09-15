package cm.ex.merch.entity;

import cm.ex.merch.entity.product.Category;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter product data")
    @Column(name = "name")
    private String name;

    @NotBlank(message="please enter product data")
    @Column(name = "game_title")
    private String gameTitle;

    @NotBlank(message="please enter product data")
    @Column(name = "description")
    private String description;

    @NotBlank(message="please enter product data")
    @Column(name = "brand")
    private String brand;

    @NotBlank(message="please enter product data")
    @Column(name = "price")
    private double price;

    @NotBlank(message="please enter product data")
    @Column(name = "quantity")
    private int quantity;

//    @NotBlank(message="please enter product data")
    @Column(name = "imageUrl")
    private String imageUrl;

//    @NotBlank(message="please enter product data")
    @Column(name = "imageUrls")
    private String[] imageUrls;

    @NotBlank(message="please enter product data")
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @NotBlank(message="please enter product data")
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

//    @NotBlank(message="please enter product data")
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
