package cm.ex.merch.dto.request.review;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewDto {

    @NotBlank(message="please enter review data")
    private String review;

    @NotBlank(message="please enter review data")
    private int priority;

    @NotBlank(message="please enter review data")
    private String userId;

    @NotBlank(message="please enter review data")
    private String productId;
}
