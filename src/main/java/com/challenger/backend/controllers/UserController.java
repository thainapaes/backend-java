package com.challenger.backend.controllers;

import com.challenger.backend.entities.User;
import com.challenger.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = "application/json")
@PreAuthorize("permitAll()")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> allUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatusCode> deleteUser(@PathVariable @Valid @RequestBody Long id) {
        HttpStatus httpResponse = userService.deleteUser(id);
        if (httpResponse.equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(HttpStatusCode.valueOf(200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatusCode.valueOf(404));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updateUser = userService.updateUser(id, user);

        if (updateUser != null) {
           return ResponseEntity.status(HttpStatus.OK).body(updateUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
