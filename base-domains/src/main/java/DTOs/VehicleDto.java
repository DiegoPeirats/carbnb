package DTOs;

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
public class VehicleDto {
	
	private Long id;
	
	private String brand;
	
	private String plate;
	
	private String address;
	
	private Integer year;
	
	private Double kms;
	
	private Long price;
	
	private byte[] image1;
	
	private byte[] image2;
	
	private byte[] image3;
	
	private byte[] image4;
	
	private VehicleType type;
	
	private StatusType status;

}