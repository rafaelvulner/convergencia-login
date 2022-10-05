package br.com.convergencia.testejavar1.controller;

import br.com.convergencia.testejavar1.entity.dtos.LoginDTO;
import br.com.convergencia.testejavar1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Validated LoginDTO user){
        return ResponseEntity.ok(this.userService.save(user));

    }

}
