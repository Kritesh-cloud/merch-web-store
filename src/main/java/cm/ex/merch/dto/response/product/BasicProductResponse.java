package cm.ex.merch.dto.response.product;

import cm.ex.merch.dto.response.basic.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicProductResponse extends Response {

    private String[] remarks;

    public BasicProductResponse(boolean status, String message, String[] remarks) {
        super(status, message);
        this.remarks = remarks;
    }

}
