package cm.ex.merch.dto.request.review;

import jakarta.validation.constraints.NotBlank;

public class ReviewStatusDto {

    @NotBlank(message="please enter review data")
    private int priority;

    @NotBlank(message="please enter review data")
    private boolean hidden;

    @NotBlank(message="please enter review data")
    private String reviewId;

    @NotBlank(message="please enter review data")
    private String userId;

}
