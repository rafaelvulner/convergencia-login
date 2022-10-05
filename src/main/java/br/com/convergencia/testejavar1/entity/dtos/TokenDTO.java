package br.com.convergencia.testejavar1.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
	
	private String type;
	private String token;

}
