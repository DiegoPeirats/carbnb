package requests;

import java.time.LocalDate;
import java.util.List;

import DTOs.TransactionDto;
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
public class PriceCalculationRequest {
	
	private Long vehicleId;
	
	private LocalDate initialDate;
	
	private LocalDate finalDate;
	
	private List<TransactionDto> customerTransactionHistorial;

}
