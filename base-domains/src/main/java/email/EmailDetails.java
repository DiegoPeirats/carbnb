package email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EmailDetails {
	
	private String recipient;
	
	private String messageBody;
	
	private String subject;
	
	private String attachment;

}
