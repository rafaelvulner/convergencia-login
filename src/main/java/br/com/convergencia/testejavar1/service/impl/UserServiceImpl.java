package br.com.convergencia.testejavar1.service.impl;

import br.com.convergencia.testejavar1.entity.User;
import br.com.convergencia.testejavar1.entity.dtos.LoginDTO;
import br.com.convergencia.testejavar1.exceptions.InvalidCpfException;
import br.com.convergencia.testejavar1.repository.UserRepository;
import br.com.convergencia.testejavar1.service.UserService;
import br.com.convergencia.testejavar1.util.CpfValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public LoginDTO save(LoginDTO login) {

        if (!CpfValidator.isValid(login.getCpf()))
            throw new InvalidCpfException("CPF invalido!");

        if(this.userRepository.findByCpf(login.getCpf()).isPresent())
            throw new RuntimeException("CPF já cadastrado!");

        login.setPassword(this.encoder.encode(login.getPassword()));
        return new LoginDTO(this.userRepository.save(new User(login)));
    }
}
