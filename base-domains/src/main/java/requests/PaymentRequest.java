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
public class PaymentRequest {
	
	private String ownerStripeId;
	private String customerStripeId;
	private Long price;
	private Long offerId;
	private String successUrl;
	private String failureUrl;

}
