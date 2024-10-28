package com.example.CapstoneBackend.Controller;

import com.example.CapstoneBackend.dto.CheckoutItemDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostMapping("/create-checkout-session")
    public Map<String, Object> createCheckoutSession(@RequestBody List<CheckoutItemDTO> checkoutItems) {
        Stripe.apiKey = stripeApiKey;

        List<SessionCreateParams.LineItem> sessionItems = checkoutItems.stream().map(item ->
                SessionCreateParams.LineItem.builder()
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("eur")
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item.getProductName())
                                                        .build()
                                        )
                                        .setUnitAmount((long) (item.getProductPrice() * 100))
                                        .build()
                        )
                        .setQuantity((long) item.getQuantity())
                        .build()
        ).toList();

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/success")
                .setCancelUrl("http://localhost:4200/cancel")
                .addAllLineItem(sessionItems)
                .build();

        try {
            Session session = Session.create(params);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", session.getId());
            return responseData;

        } catch (StripeException e) {
            throw new RuntimeException("Errore nella creazione della sessione di pagamento", e);
        }
    }
}
