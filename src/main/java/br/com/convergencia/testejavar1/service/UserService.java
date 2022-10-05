package br.com.convergencia.testejavar1.service;

import br.com.convergencia.testejavar1.entity.dtos.LoginDTO;

public interface UserService {

    LoginDTO save(LoginDTO user);
}
