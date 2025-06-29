package com.example.demo.infrastructure.controller;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.checkout.Session;
import com.example.demo.domain.entity.StripeEvent;
import com.example.demo.infrastructure.repository.StripeEventRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;


@RestController
@RequestMapping("/webhook/stripe")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;
    
    @Autowired
    private StripeEventRepository repository;

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            StripeEvent stripeEvent = new StripeEvent(
            	    event.getId(),
            	    event.getType(),
            	    OffsetDateTime.ofInstant(Instant.ofEpochSecond(event.getCreated()), ZoneOffset.UTC),
            	    event.getApiVersion(),
            	    event.getLivemode()
            	);
            
            repository.save(stripeEvent);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // Maneja eventos específicos
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            if (session != null) {
                // Marcar reserva como pagada, actualizar base de datos, enviar email, etc.
            }
        }

        return ResponseEntity.ok("Event received");
    }
}

