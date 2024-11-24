package cm.ex.merch.dto.request.stripe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeRequestDto {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
}
