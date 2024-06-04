package com.challenger.backend.controllers;

import com.challenger.backend.entities.User;
import com.challenger.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class LoginController {

    UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/signin")
    public ResponseEntity<User> login(@Valid @RequestBody String login, @Valid @RequestBody String password) {
        User loginUser = userService.getLogin(login, password);

        if (loginUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(loginUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //return ResponseEntity.created(URI.create("/vehicles/" + createdVehicle.getId())).body(createdVehicle);
    }
}
