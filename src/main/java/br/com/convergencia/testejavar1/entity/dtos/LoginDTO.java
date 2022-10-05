package br.com.convergencia.testejavar1.entity.dtos;

import br.com.convergencia.testejavar1.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
	
	private String cpf;
	private String password;

	public LoginDTO(User user){
		this.cpf = user.getCpf();
		this.password = user.getPassword();
	}

}
