package cm.ex.merch.dto.response.review;

import cm.ex.merch.dto.response.basic.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicReviewResponse extends Response {

    private String[] remarks;

    private String userReviewId;

    public BasicReviewResponse(boolean status, String message, String[] remarks, String userReviewId) {
        super(status, message);
        this.remarks = remarks;
        this.userReviewId = userReviewId;
    }
}
