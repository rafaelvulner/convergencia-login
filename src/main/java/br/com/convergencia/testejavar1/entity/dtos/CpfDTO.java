package br.com.convergencia.testejavar1.entity.dtos;

import br.com.convergencia.testejavar1.entity.enums.StatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CpfDTO {

    private String cpf;

    private StatusEnum status;


}
