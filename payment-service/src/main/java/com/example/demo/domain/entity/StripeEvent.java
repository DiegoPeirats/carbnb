package com.example.demo.domain.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name= "stripeEvents")
public class StripeEvent {
	
	@Id
	private String id;     
	
    private String type;               
    private OffsetDateTime createdAt;  
    private String apiVersion;         
    private boolean livemode; 

}
