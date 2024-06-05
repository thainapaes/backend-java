package com.challenger.backend.controllers;

import com.challenger.backend.dto.LoginRequestDTO;
import com.challenger.backend.dto.ResponseDTO;
import com.challenger.backend.infra.sercurity.TokenService;
import com.challenger.backend.repository.IUserRepository;
import com.challenger.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    IUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        ResponseDTO responseDTO = this.userService.validatePass(body.login(), body.password());

        if(responseDTO != null) {
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.badRequest().build();
    }

}
