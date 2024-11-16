package cm.ex.merch.dto.request.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterProductDto {

    private String name;

    private String brand;

    private String gameTitle;

    private String category;

    // yes, no, both [yes = product stock > 0, no product stock = 0, both = product stock >= 0]
    private String availability;

    private int pageNumber;

    private int pageSize;
}
