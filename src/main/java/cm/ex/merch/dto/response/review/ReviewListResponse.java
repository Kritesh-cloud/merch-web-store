package cm.ex.merch.dto.response.review;

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
public class ReviewListResponse extends Response {
    List<ReviewDto> reviewDtoList;

    public ReviewListResponse(boolean status, String message, List<ReviewDto> reviewDtoList) {
        super(status, message);
        this.reviewDtoList = reviewDtoList;
    }
}
