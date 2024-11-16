package cm.ex.merch.entity;

import cm.ex.merch.entity.image.Image;
import cm.ex.merch.entity.product.Category;
import cm.ex.merch.entity.user.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Product(String name, String gameTitle, String description, String brand, double price, int quantity, String status, Category category) {
        this.name = name;
        this.gameTitle = gameTitle;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.category = category;
    }

    public Product(String name, String gameTitle, String description, String brand, double price, int quantity, LocalDateTime createdAt, LocalDateTime updatedAt, String status, Category category) {
        this.name = name;
        this.gameTitle = gameTitle;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.category = category;
    }
}
