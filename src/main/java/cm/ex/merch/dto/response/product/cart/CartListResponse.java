package cm.ex.merch.dto.response.product.cart;

import cm.ex.merch.dto.response.basic.Response;
import cm.ex.merch.entity.Product;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartListResponse extends Response {

    private String cartId;
    List<Product> productList;
}
