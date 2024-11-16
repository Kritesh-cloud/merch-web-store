package cm.ex.merch.dto.response.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private String id;

    private String review;

    private int priority;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String userId;

    private String productId;
}
