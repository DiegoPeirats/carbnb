package com.diego_peirats.carbnb.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vehicle.StatusType;
import vehicle.VehicleType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name= "users")
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long ownerId;
	
	private String brand;
	
	private String address;
	
	private Integer year;
	
	private Integer basePrice;
	
	private Double kms;
	
	@Lob
	private byte[] image1;

	@Lob
	private byte[] image2;

	@Lob
	private byte[] image3;
	
	@Lob
	private byte[] image4;
	
	private StatusType status;
	
	private VehicleType type;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime modifiedAt;

}