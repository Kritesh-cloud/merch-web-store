package cm.ex.merch.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDto {

    @NotBlank(message="please enter product data")
    private String name;

    @NotBlank(message="please enter product data")
    private String gameTitle;

    @NotBlank(message="please enter product data")
    private String description;

    @NotBlank(message="please enter product data")
    private String brand;

    @NotBlank(message="please enter product data")
    private double price;

    @NotBlank(message="please enter product data")
    private int quantity;

    private String imageUrl;

    private String[] imageUrls;

    // What is the use of status? New, Hot
    private String status;

    @NotBlank(message="please enter product data")
    private String category;
}
