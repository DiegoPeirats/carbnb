package requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import offer.OfferStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferCreationRequest {
	
	private Long vehicleId;
	private Long userId;
	private Integer pricePerDay;
	private OfferStatus status;

}
