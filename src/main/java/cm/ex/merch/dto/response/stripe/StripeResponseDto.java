package cm.ex.merch.dto.response.stripe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeResponseDto {
    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;
}
