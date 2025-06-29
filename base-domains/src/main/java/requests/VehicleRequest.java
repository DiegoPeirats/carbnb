package requests;

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
public class VehicleRequest {
	
	private Long ownerId;
	
	private String plate;
	
	private String brand;
	
	private String address;
	
	private Integer year;
	
	private Double kms;
	
	private byte[] image1;
	
	private byte[] image2;
	
	private byte[] image3;
	
	private byte[] image4;
	
	private VehicleType type;
	
	private StatusType status;

}
