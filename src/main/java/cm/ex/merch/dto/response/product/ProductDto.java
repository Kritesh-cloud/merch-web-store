package cm.ex.merch.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private UUID id;

    private String name;

    private String gameTitle;

    private String description;

    private String brand;

    private double price;

    private int quantity;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String status;

    private String category;

    private String[] imageUrlList;
}
