package br.com.convergencia.testejavar1.service.impl;

import br.com.convergencia.testejavar1.entity.dtos.CpfDTO;
import br.com.convergencia.testejavar1.entity.dtos.LoginDTO;
import br.com.convergencia.testejavar1.entity.dtos.StatusCpfDTO;
import br.com.convergencia.testejavar1.entity.dtos.TokenDTO;
import br.com.convergencia.testejavar1.exceptions.InvalidCpfException;
import br.com.convergencia.testejavar1.service.CpfService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Log4j2
public class CpfServiceImpl implements CpfService {

    private static final String REGISTRATION_URL = "http://localhost:8082/cpf";
    private static final String AUTHENTICATION_URL = "http://localhost:8082/auth";

    private static final String UPDATE_STATUS = "http://localhost:8082/cpf/status";
    private static final String STATUS = "http://localhost:8082/cpf/";

    @Value("${jwt.user}")
    private String user;

    @Value("${jwt.password}")
    private String password;

    private final RestTemplate restTemplate;

    public CpfServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public CpfDTO saveCpf(CpfDTO cpf) {

        String body = getBody(cpf);

        HttpEntity<String> jwtEntity = new HttpEntity<>(body,getJwt());

        ResponseEntity<CpfDTO> response = restTemplate.exchange(REGISTRATION_URL, HttpMethod.POST, jwtEntity,
                CpfDTO.class);

        return response.getBody();
    }

    @Override
    public StatusCpfDTO findStatusCpf(String cpf) {

        HttpEntity<String> jwtEntity = new HttpEntity<>(getJwt());

        ResponseEntity<StatusCpfDTO> response = restTemplate.exchange(STATUS+cpf, HttpMethod.GET, jwtEntity,
                StatusCpfDTO.class);

        return response.getBody();
    }


    @Override
    public StatusCpfDTO updateStatus(StatusCpfDTO status) {

        String body = getBody(status);

        HttpEntity<String> jwtEntity = new HttpEntity<>(body,getJwt());
        
        ResponseEntity<StatusCpfDTO> response = restTemplate.exchange(UPDATE_STATUS, HttpMethod.PUT, jwtEntity,
                StatusCpfDTO.class);

        return response.getBody();
    }

   

    private HttpHeaders getJwt() {
        LoginDTO loginDTO = LoginDTO.builder()
                .cpf(this.user)
                .password(this.password)
                .build();

        String body = getBody(loginDTO);

        HttpHeaders authenticationHeaders = getHeaders();

        HttpEntity<String> authenticationEntity = new HttpEntity<>(body,
                authenticationHeaders);

        ResponseEntity<TokenDTO> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL,
                HttpMethod.POST, authenticationEntity, TokenDTO.class);

        if (authenticationResponse.getStatusCode().equals(HttpStatus.valueOf(403)))
            throw new InvalidCpfException("Falha ao autenticar");

        String token = "Bearer " + Objects.requireNonNull(authenticationResponse.getBody()).getToken();
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        return headers;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }


    private String getBody(final Object user) {

        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
            throw new InvalidCpfException(ex.getMessage());
        }
    }
}
