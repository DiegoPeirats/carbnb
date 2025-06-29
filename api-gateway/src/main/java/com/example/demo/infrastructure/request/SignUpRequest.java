package com.example.demo.infrastructure.request;

import com.example.demo.application.security.annotation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class SignUpRequest {
    @Email
    @NotBlank
    private String email;
    @ValidPassword
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
	
	private String lastName;
	
	private String gender;
	
	private String address;
	
	private String stateOfOrigin;
	
	private String accountNumber;
	
	private String phoneNumber;
	
	private byte[] profileImage;
}
	
