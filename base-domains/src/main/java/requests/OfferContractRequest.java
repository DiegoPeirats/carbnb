package requests;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferContractRequest {
	
	private Long offerId;
	
	private Long ownerId;
	
	private String ownerStripeId;
	
	private Long customerId;
	
	private String customerStripeId;
	
	private Integer basePrice;
	
	private LocalDate initialDate;
	
	private LocalDate finalDate;
	
	public Long getDaysOfContract() {
		return ChronoUnit.DAYS.between(initialDate, finalDate);
	}
	
	public Long getPrice() {
		return basePrice * getDaysOfContract();
	}

}
