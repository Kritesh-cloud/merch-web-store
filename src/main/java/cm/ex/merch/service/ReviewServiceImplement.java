package cm.ex.merch.service;

import cm.ex.merch.dto.request.review.AddReviewDto;
import cm.ex.merch.dto.request.review.ReviewStatusDto;
import cm.ex.merch.dto.request.review.UpdateReviewDto;
import cm.ex.merch.dto.response.review.BasicReviewResponse;
import cm.ex.merch.dto.response.review.ReviewDto;
import cm.ex.merch.dto.response.review.ReviewListResponse;
import cm.ex.merch.entity.Product;
import cm.ex.merch.entity.User;
import cm.ex.merch.entity.product.Review;
import cm.ex.merch.repository.*;
import cm.ex.merch.security.authentication.UserAuth;
import cm.ex.merch.service.interfaces.ReviewService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class ReviewServiceImplement implements ReviewService {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ReviewServiceImplement.class);

    @Override
    public BasicReviewResponse addReview(AddReviewDto addReviewDto) throws AccessDeniedException, IllegalAccessException {
        BasicReviewResponse basicReviewResponse = new BasicReviewResponse(true, "New review added successfully", null, null);
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied");

        Review review = reviewRepository.findReviewByProductAndUserIds(addReviewDto.getProductId(), addReviewDto.getUserId());
        if (review != null) throw new IllegalAccessException("Review exists, cannot add new review.");

        review = modelMapper.map(addReviewDto, Review.class);
        User user = userRepository.findUserByUserId(addReviewDto.getUserId());
        Product product = productRepository.findProductById(addReviewDto.getProductId());
        if (user == null) throw new NoSuchElementException("User not found");
        if (product == null) throw new NoSuchElementException("Product not found");

        review.setUser(user);
        review.setProduct(product);
        reviewRepository.save(review);

        Review userReview = reviewRepository.findReviewByProductAndUserIds(addReviewDto.getProductId(), addReviewDto.getUserId());
        basicReviewResponse.setUserReviewId(userReview.getId().toString());
        return basicReviewResponse;
    }

    @Override
    public ReviewListResponse reviewList(String productId) {
        ReviewListResponse reviewListResponse = new ReviewListResponse(true, "All review list for this product", null);
        List<Review> reviewList = reviewRepository.findAll();
        List<ReviewDto> reviewDtoList = reviewList.stream()
                .map(review -> {
                    ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);
                    reviewDto.setId(review.getId().toString());
                    reviewDto.setUserId(review.getUser().getId().toString());
                    reviewDto.setProductId(review.getProduct().getId().toString());
                    return reviewDto;
                })
                .toList();
        reviewListResponse.setReviewDtoList(reviewDtoList);

        return reviewListResponse;
    }

    @Override
    public ReviewListResponse reviewListWithPriority(String productId) {
        ReviewListResponse reviewListResponse = new ReviewListResponse(true, "Review list for this product", null);
        List<Review> reviewList = reviewRepository.findReviewByPriority(0, 10);
        List<ReviewDto> reviewDtoList = reviewList.stream()
                .map(review -> {
                    ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);
                    reviewDto.setId(review.getId().toString());
                    reviewDto.setUserId(review.getUser().getId().toString());
                    reviewDto.setProductId(review.getProduct().getId().toString());
                    return reviewDto;
                })
                .toList();
        reviewListResponse.setReviewDtoList(reviewDtoList);
        return reviewListResponse;
    }

    @Override
    public ReviewListResponse reviewListWithPriorityAndUserId(String productId, String userId) {
        ReviewListResponse reviewListResponse = reviewListWithPriority(productId);
        Review review = reviewRepository.findReviewByProductAndUserIds(productId,userId);
        if(review == null) return reviewListResponse;

        ReviewDto reviewDto = modelMapper.map(review,ReviewDto.class);
        reviewDto.setId(review.getId().toString());
        reviewDto.setUserId(review.getUser().getId().toString());
        reviewDto.setProductId(review.getProduct().getId().toString());

        reviewListResponse.setReviewDtoList(Collections.singletonList(reviewDto));
        return reviewListResponse;
    }

    @Override
    public ReviewListResponse reviewListById(String reviewId) {
        ReviewListResponse reviewListResponse = new ReviewListResponse(true, "Review list for this product", null);

        Review review = reviewRepository.findReviewById(reviewId);
        ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);
        reviewDto.setId(review.getId().toString());
        reviewDto.setUserId(review.getUser().getId().toString());
        reviewDto.setProductId(review.getProduct().getId().toString());

        reviewListResponse.setReviewDtoList(Collections.singletonList(reviewDto));
        return reviewListResponse;
    }

    @Override
    public BasicReviewResponse editReview(UpdateReviewDto updateReviewDto) throws AccessDeniedException {

        BasicReviewResponse basicReviewResponse = new BasicReviewResponse(true, "Review added successfully", null, null);
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied");

        Review review = modelMapper.map(updateReviewDto, Review.class);
        User user = userRepository.findUserByUserId(updateReviewDto.getUserId());
        Product product = productRepository.findProductById(updateReviewDto.getProductId());
        if (user == null) throw new NoSuchElementException("User not found");
        if (product == null) throw new NoSuchElementException("Product not found");

        review.setId(UUID.fromString(updateReviewDto.getId()));
        review.setUser(user);
        review.setProduct(product);
        reviewRepository.save(review);

        Review userReview = reviewRepository.findReviewByProductAndUserIds(updateReviewDto.getProductId(), updateReviewDto.getUserId());
        basicReviewResponse.setUserReviewId(userReview.getId().toString());
        return basicReviewResponse;
    }

    @Override
    public BasicReviewResponse editStatusPriority(ReviewStatusDto reviewStatusDto) {
        return null;
    }

    @Override
    public BasicReviewResponse editStatusHidden(ReviewStatusDto reviewStatusDto) {
        return null;
    }

    @Override
    public BasicReviewResponse deleteReview(String reviewId) throws AccessDeniedException {
        BasicReviewResponse basicReviewResponse = new BasicReviewResponse(true, "Review deleted successfully", null, null);
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if (!userAuth.isAuthenticated()) throw new AccessDeniedException("Access denied");

        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) throw new NoSuchElementException("Review doesn't exists");

        User user = userRepository.findUserByUserId(review.getUser().getId().toString());
        if(user==null || !user.getEmail().equals(userAuth.getEmail())) throw new AccessDeniedException("Access denied");

        reviewRepository.delete(review);

        return basicReviewResponse;
    }
}
