package com.challenger.backend.controllers;

import com.challenger.backend.entities.Usuario;
import com.challenger.backend.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = "application/json")
public class UsuarioController {
    UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> allUsuarios() {
        return ResponseEntity.ok(usuarioService.allUsuarios());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUsuario(id));
    }

    @PostMapping("/users")
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario createdUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
        //return ResponseEntity.created(URI.create("/vehicles/" + createdVehicle.getId())).body(createdVehicle);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable @Valid @RequestBody Long id) {
        HttpStatus httpResponse = usuarioService.deleteUsuario(id);
        return ResponseEntity.status(httpResponse).body(
                httpResponse.equals(HttpStatus.NOT_FOUND) ? "Ocorreu um erro no momento da deleçao" : "Usuario excluido");
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        Usuario updateUsuario = usuarioService.updateUsuario(id, usuario);

        if (updateUsuario != null) {
           return ResponseEntity.status(HttpStatus.OK).body("Usuario atualizado");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro durante a atualizaçao");
        }
    }
}
