package cm.ex.merch.dto.response.product.cart;

import cm.ex.merch.dto.response.basic.Response;
import cm.ex.merch.entity.product.ProductQuantity;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartListResponse extends Response {

    private String cartId;
    List<ProductQuantity> productQuantityList;

    public CartListResponse(boolean status, String message, String cartId, List<ProductQuantity> productQuantityList) {
        super(status, message);
        this.cartId = cartId;
        this.productQuantityList = productQuantityList;
    }
}
