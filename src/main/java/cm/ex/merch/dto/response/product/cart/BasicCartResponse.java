package cm.ex.merch.dto.response.product.cart;

import cm.ex.merch.dto.response.basic.Response;
import cm.ex.merch.entity.product.ProductQuantity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicCartResponse extends Response {

    private String[] remarks;

    public BasicCartResponse(boolean status, String message, String[] remarks) {
        super(status, message);
        this.remarks = remarks;
    }
}
