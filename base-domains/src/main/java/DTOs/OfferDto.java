package DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import offer.OfferStatus;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {
	
	private Long id;
	private Long vehicleId;
	private Long userId;
	private Integer pricePerDay;
	private OfferStatus status;
}
