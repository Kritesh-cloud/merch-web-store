package cm.ex.merch.service.interfaces;

import cm.ex.merch.dto.request.review.AddReviewDto;
import cm.ex.merch.dto.request.review.ReviewStatusDto;
import cm.ex.merch.dto.request.review.UpdateReviewDto;
import cm.ex.merch.dto.response.review.BasicReviewResponse;
import cm.ex.merch.dto.response.review.ReviewListResponse;

import java.nio.file.AccessDeniedException;

public interface ReviewService {

    // CREATE
    public BasicReviewResponse addReview(AddReviewDto addReviewDto) throws AccessDeniedException, IllegalAccessException;

    // READ
    public ReviewListResponse reviewList(String productId);
    public ReviewListResponse reviewListWithPriority(String productId);
    public ReviewListResponse reviewListWithPriorityAndUserId(String productId, String userId);
    public ReviewListResponse reviewListById(String reviewId);

    // UPDATE
    public BasicReviewResponse editReview(UpdateReviewDto updateReviewDto) throws AccessDeniedException;
    public BasicReviewResponse editStatusPriority(ReviewStatusDto reviewStatusDto);
    public BasicReviewResponse editStatusHidden(ReviewStatusDto reviewStatusDto);

    // DELETE
    public BasicReviewResponse deleteReview(String reviewId) throws AccessDeniedException;
}


/*

#create
basic review response : addReview (addReviewDto) [user]

#read
review response with reviews : read review of a product (product id) [guest]
review response with reviews : read review of a product by priority + user review at top (product id) [guest, user]

#update
basic review response : edit review by id (review id, user id)  [self, moderator]
basic review response : manage priority by id (review id, user id) [moderator]
basic review response : manage hidden status by id (review id, user id) [moderator]

#delete
basic review response : delete review by id (review id, user id, product id) [self, moderator]

*/


/*

Kritesh Thapa


*/