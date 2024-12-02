package cm.ex.merch.controller;

import cm.ex.merch.service.ReviewServiceImplement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product-review")
@RestController
public class ReviewController {

    final ReviewServiceImplement reviewServiceImplement;

    public ReviewController(ReviewServiceImplement reviewServiceImplement) {
        this.reviewServiceImplement = reviewServiceImplement;
    }
/*
    @GetMapping("/me")
    public ResponseEntity<BasicReviewResponse> addReview(@RequestBody AddReviewDto addReviewDto) throws AccessDeniedException {
        return new ResponseEntity<BasicReviewResponse>(reviewServiceImplement.addReview(addReviewDto), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ReviewListResponse> addReview(@RequestBody AddReviewDto addReviewDto) throws AccessDeniedException {
        return new ResponseEntity<ReviewListResponse>(reviewServiceImplement.addReview(addReviewDto), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ReviewListResponse> addReview(@RequestBody AddReviewDto addReviewDto) throws AccessDeniedException {
        return new ResponseEntity<ReviewListResponse>(reviewServiceImplement.addReview(addReviewDto), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ReviewListResponse> addReview(@RequestBody AddReviewDto addReviewDto) throws AccessDeniedException {
        return new ResponseEntity<ReviewListResponse>(reviewServiceImplement.addReview(addReviewDto), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ReviewListResponse> addReview(@RequestBody AddReviewDto addReviewDto) throws AccessDeniedException {
        return new ResponseEntity<ReviewListResponse>(reviewServiceImplement.addReview(addReviewDto), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<BasicReviewResponse> addReview(@RequestBody AddReviewDto addReviewDto) throws AccessDeniedException {
        return new ResponseEntity<BasicReviewResponse>(reviewServiceImplement.addReview(addReviewDto), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<BasicReviewResponse> addReview(@RequestBody AddReviewDto addReviewDto) throws AccessDeniedException {
        return new ResponseEntity<BasicReviewResponse>(reviewServiceImplement.addReview(addReviewDto), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<BasicReviewResponse> addReview(@RequestBody AddReviewDto addReviewDto) throws AccessDeniedException {
        return new ResponseEntity<BasicReviewResponse>(reviewServiceImplement.addReview(addReviewDto), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<BasicReviewResponse> addReview(@RequestBody AddReviewDto addReviewDto) throws AccessDeniedException {
        return new ResponseEntity<BasicReviewResponse>(reviewServiceImplement.addReview(addReviewDto), HttpStatus.OK);
    }
*/
}
