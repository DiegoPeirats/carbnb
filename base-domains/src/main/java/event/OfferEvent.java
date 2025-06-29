package event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import offer.OfferStatus;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferEvent {
	
	private Long vehicleId;
	private Long userId;
	private OfferStatus status;

}
