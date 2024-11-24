package cm.ex.merch.controller;

import cm.ex.merch.dto.request.stripe.StripeRequestDto;
import cm.ex.merch.dto.response.stripe.StripeResponseDto;
import cm.ex.merch.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponseDto> checkoutProducts(@RequestBody StripeRequestDto stripeRequestDto) {
        StripeResponseDto stripeResponse = stripeService.checkoutProducts(stripeRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}
