package requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest {
	
	private String firstName;
	
	private String lastName;
	
	private String gender;
	
	private String address;
	
	private String stateOfOrigin;
	
	private String accountNumber;
	
	private String email;
	
	private String password;
	
	private String phoneNumber;
	
	private byte[] profileImage;

}
