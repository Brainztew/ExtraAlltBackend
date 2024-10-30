package com.AskABot.AskABot.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;



@RestController
@RequestMapping("/stripe")
@CrossOrigin(origins = {"http://localhost:5173", "https://coral-app-ei5fb.ondigitalocean.app"})
public class StripeController {

    @Value("${stripeKey}")
    private String stripeKey;

    @PostMapping("/checkout")
    public Map<String, Object> createCheckoutSession(@RequestBody Map<String, Object> request) throws Exception {
        Stripe.apiKey = stripeKey;

        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");

        SessionCreateParams.LineItem[] lineItems = items.stream().map(item -> {
            long price = ((Number) item.get("price")).longValue();
            long quantity = ((Number) item.get("quantity")).longValue();

            return SessionCreateParams.LineItem.builder()
                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmount(price * 100) 
                            .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName((String) item.get("title"))
                                    .build())
                            .build())
                    .setQuantity(quantity)
                    .build();
        }).toArray(SessionCreateParams.LineItem[]::new);

        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(Arrays.asList(lineItems))
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://coral-app-ei5fb.ondigitalocean.app/?page=SuccessPage")
                .setCancelUrl("https://coral-app-ei5fb.ondigitalocean.app/?page=CancelPage")
                .setShippingAddressCollection(
                    SessionCreateParams.ShippingAddressCollection.builder()
                        .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.SE)
                        .build()
                )
                .build();

        Session session = Session.create(params);

        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        return response;
    }
}
