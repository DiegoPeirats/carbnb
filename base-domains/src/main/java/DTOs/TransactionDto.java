package DTOs;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
	
	private Long vehicleId;
	private Long ownerId;
	private Long customerId;
	private Double totalPrice;
	
	private LocalDateTime createdAt;
	
}
