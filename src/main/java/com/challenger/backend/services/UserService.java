package com.challenger.backend.services;

import com.challenger.backend.entities.Car;
import com.challenger.backend.entities.User;
import com.challenger.backend.exceptions.AppException;
import com.challenger.backend.repository.IUserRepository;
import com.challenger.backend.repository.ICarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    ICarRepository carRepository;
    @Autowired
    CarService carService;

    BCryptPasswordEncoder cript = new BCryptPasswordEncoder();

    public Iterable<User> allUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        return user;
    }

    public User getLogin(String login, String password) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isPresent()) {
            String passwordCript = cript.encode(user.get().getPassword());
            if (!cript.matches(password, passwordCript)) {
                return user.get();
            }
        } else {
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }

        return user.get();
    }

    public User saveUser(User user) {
        if (!user.getEmail().isEmpty()) {
            Optional<User> jaExiste = userRepository.findByEmail(user.getEmail());
            if(jaExiste.isPresent()) {
                //verificar as mensagens de retorno
                throw new AppException("Email do usuário já foi cadastrado", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new AppException("O e-mail fornecido está inválido", HttpStatus.BAD_REQUEST);
        }

        if (!user.getLogin().isEmpty()) {
            Optional<User> loginExisted = userRepository.findByLogin(user.getLogin());
            if (loginExisted.isPresent()) {
                throw new AppException("Login já está sendo utilizado", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new AppException("O campo de login nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (!user.getPassword().isEmpty()) {
            String senhaCriptografada = cript.encode(user.getPassword());
            user.setPassword(senhaCriptografada);
        } else {
            throw new AppException("O campo da senha nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (user.getFirstName().isEmpty()) {
            throw new AppException("O campo do nome nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (user.getLastName().isEmpty()) {
            throw new AppException("O campo do sobrenome nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (user.getPhone().isEmpty()) {
            throw new AppException("O campo do telefone nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (user.getBirthday().after(new Date()) && !user.getBirthday().toString().isEmpty()) {
            throw new AppException("A data de nascimento está invalida", HttpStatus.BAD_REQUEST);
        }

        if (!user.getCarsList().isEmpty()) {
            List<Car> cars = new ArrayList<>();
            for(Car car : user.getCarsList()) {
                Optional<Car> carExisted = carRepository.findByLicensePlate(car.getLicensePlate());
                if (carExisted.isEmpty()) {
                    Car c = carService.saveVehicle(car);
                    cars.add(c);
                }
            }
            user.setCarsList(cars);
        }

        user.setCreatedDate(new Date());

        return userRepository.save(user);
    }

    public HttpStatus deleteUser(Long id) {
        Optional<User> clientOptional = userRepository.findById(id);
        if(clientOptional.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        } else {
            userRepository.delete(clientOptional.get());
            return HttpStatus.OK;
        }
    }

    public User updateUser(Long id, User user) {
        Optional<User> usuarioOptional = userRepository.findById(id);
        User u = null;
        if (usuarioOptional.isPresent()) {
            u = usuarioOptional.get();

            if (!user.getFirstName().isEmpty() &&
                    !user.getFirstName().equals(u.getFirstName())) {
                u.setFirstName(user.getFirstName());
            }

            if (!user.getLastName().isEmpty() && !user.getLastName().equals(u.getLastName())) {
                u.setLastName(user.getLastName());
            }

            if (!user.getEmail().isEmpty() && !user.getEmail().equals(u.getEmail())) {
                u.setEmail(user.getEmail());
            }

            if (!user.getBirthday().after(new Date()) && u.getBirthday().after(user.getBirthday())) {
                //melhorar a condiçao para atualizar
                u.setBirthday(user.getBirthday());
            }

            if (!user.getPhone().isEmpty() &&
                    !user.getPhone().equals(u.getPhone())) {
                u.setPhone(user.getPhone());
            }

            if (!user.getLogin().isEmpty() && !user.getLogin().equals(u.getLogin())) {
                //validar se já em outro login cadastrado
                u.setLogin(user.getLogin());
            }

            if (!user.getPassword().isEmpty() && !user.getPassword().equals(u.getPassword())) {
                String passwordCript = cript.encode(user.getPassword());
                if (!cript.matches(u.getPassword(), passwordCript)) {
                    u.setPassword(passwordCript);
                }
            }

            return userRepository.save(u);
        }

        return u;
    }
}
