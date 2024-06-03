package com.challenger.backend.services;

import com.challenger.backend.entities.Car;
import com.challenger.backend.entities.Usuario;
import com.challenger.backend.exceptions.AppException;
import com.challenger.backend.repository.IUsuarioRepository;
import com.challenger.backend.repository.ICarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    ICarRepository carRepository;
    @Autowired
    CarService carService;

    BCryptPasswordEncoder cript = new BCryptPasswordEncoder();

    public List<Usuario> allUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new AppException("Usuario not found", HttpStatus.NOT_FOUND));
        return usuario;
    }

    public Usuario saveUsuario(Usuario usuario) {
        if (!usuario.getEmail().isEmpty()) {
            Optional<Usuario> jaExiste = usuarioRepository.findByEmail(usuario.getEmail());
            if(jaExiste.isPresent()) {
                //verificar as mensagens de retorno
                throw new AppException("Email do usuário já foi cadastrado", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new AppException("O e-mail fornecido está inválido", HttpStatus.BAD_REQUEST);
        }

        if (!usuario.getLogin().isEmpty()) {
            Optional<Usuario> loginExisted = usuarioRepository.findByLogin(usuario.getLogin());
            if (loginExisted.isPresent()) {
                throw new AppException("Login já está sendo utilizado", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new AppException("O campo de login nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (!usuario.getPassword().isEmpty()) {
            String senhaCriptografada = cript.encode(usuario.getPassword());
            usuario.setPassword(senhaCriptografada);
        } else {
            throw new AppException("O campo da senha nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getFirstName().isEmpty()) {
            throw new AppException("O campo do nome nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getLastName().isEmpty()) {
            throw new AppException("O campo do sobrenome nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getPhone().isEmpty()) {
            throw new AppException("O campo do telefone nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (usuario.getBirthday().after(new Date()) && !usuario.getBirthday().toString().isEmpty()) {
            throw new AppException("A data de nascimento está invalida", HttpStatus.BAD_REQUEST);
        }

        if (!usuario.getCarList().isEmpty()) {
            List<Car> cars = new ArrayList<>();
            for(Car car : usuario.getCarList()) {
                Optional<Car> carExisted = carRepository.findByLicensePlate(car.getLicensePlate());
                if (carExisted.isEmpty()) {
                    Car c = carService.saveVehicle(car);
                    cars.add(c);
                }
            }
            usuario.setCarList(cars);
        }

        usuario.setCreatedDate(new Date());

        return usuarioRepository.save(usuario);
    }

    public HttpStatus deleteUsuario(Long id) {
        Optional<Usuario> clientOptional = usuarioRepository.findById(id);
        if(clientOptional.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        } else {
            usuarioRepository.delete(clientOptional.get());
            return HttpStatus.OK;
        }
    }

    public Usuario updateUsuario(Long id, Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        Usuario u = null;
        if (usuarioOptional.isPresent()) {
            u = usuarioOptional.get();

            if (!usuario.getFirstName().isEmpty() ) {
                u.setFirstName(usuario.getFirstName());
            }

            if (!usuario.getLastName().isEmpty()) {
                u.setLastName(usuario.getLastName());
            }

            if (!usuario.getEmail().isEmpty()) {
                u.setEmail(usuario.getEmail());
            }

            if (!usuario.getBirthday().after(new Date()) && u.getBirthday().after(usuario.getBirthday())) {
                //melhorar a condiçao para atualizar
                u.setBirthday(usuario.getBirthday());
            }

            if (!usuario.getPhone().isEmpty()) {
                u.setPhone(usuario.getPhone());
            }

            if (!usuario.getLogin().isEmpty() && !usuario.getLogin().equals(u.getLogin())) {
                //validar se já em outro login cadastrado
                u.setLogin(usuario.getLogin());
            }

            if (!usuario.getPassword().isEmpty() && !usuario.getPassword().equals(u.getPassword())) {
                String passwordCript = cript.encode(usuario.getPassword());
                if (!cript.matches(u.getPassword(), passwordCript)) {
                    u.setPassword(passwordCript);
                }
            }

            return usuarioRepository.save(u);
        }

        return u;
    }
}
