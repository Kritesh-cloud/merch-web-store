package cm.ex.merch.dto.response.product;

import cm.ex.merch.dto.response.basic.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse extends Response {

    private List<ProductDto> productList;

    public ProductListResponse(boolean status, String message, List<ProductDto> productList) {
        super(status, message);
        this.productList = productList;
    }
}
