package com.challenger.backend.services;

import com.challenger.backend.entities.Car;
import com.challenger.backend.entities.User;
import com.challenger.backend.exceptions.AppException;
import com.challenger.backend.repository.IUserRepository;
import com.challenger.backend.repository.ICarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    ICarRepository carRepository;
    @Autowired
    IUserRepository userRepository;

    public List<Car> allVehicles() {
        return carRepository.findAll();
    }

    public Car getVehicle(Long id, String licensePlate) {
        Optional<User> user = userRepository.findById(id);
        Car c = null;
        if (user.isPresent()) {
            for(Car car : user.get().getCarsList()) {
                if (licensePlate.equals(car.getLicensePlate())) {
                    c = car;
                }
            }
        } else {
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }
        return c;
    }

    public Car saveVehicle(Car car) {
        if (!car.getLicensePlate().isEmpty()) {
            Optional<Car> carExisted = carRepository.findByLicensePlate(car.getLicensePlate());
            if (carExisted.isPresent()) {
                throw new AppException("Placa já existente", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new AppException("O campo da placa nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (car.getColor() == null) {
            throw new AppException("O campo cor nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (car.getModel() == null) {
            throw new AppException("O campo modelo nao foi preenchido", HttpStatus.BAD_REQUEST);
        }

        if (car.getYear() <= 0) {
            throw new AppException("O campo 'Ano' foi preenchido de maneira incorreta", HttpStatus.BAD_REQUEST);
        }
        return carRepository.save(car);
    }

    public HttpStatus deleteVehicle(Long id, String licensePlate) {
        Optional<User> clientOptional = userRepository.findById(id);
        if (clientOptional.isPresent()) {
            User user = clientOptional.get();
            Car carToRemove = null;
            for (Car c : user.getCarsList()) {
                if (c.getLicensePlate().equals(licensePlate)) {
                    carToRemove = c;
                    break;
                }
            }

            if (carToRemove != null) {
                user.getCarsList().remove(carToRemove);
                userRepository.save(user); // Salva o usuário atualizado

                carRepository.delete(carToRemove); // Exclui o veículo
                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND; // Veículo não encontrado na lista do usuário
            }
        }

        return HttpStatus.NOT_FOUND;
    }

    public Car updateVehicle(Long id, Car car, String licensePlate) {
        Optional<User> userFound = userRepository.findById(id);
        Car v = null;
        if (userFound.isPresent()) {
            for(Car c : userFound.get().getCarsList()) {
                if (c.getLicensePlate().equals(licensePlate)) {
                    Optional<Car> carOptional = carRepository.findByLicensePlate(c.getLicensePlate());
                    if (carOptional.isPresent()) {
                        v = carOptional.get();
                        if (!car.getColor().isEmpty() && !car.getColor().equals(v.getColor())) {
                            v.setColor(car.getColor());
                        }

                        if (!car.getModel().isEmpty() && !car.getModel().equals(v.getModel())) {
                            v.setModel(car.getModel());
                        }

                        if (!car.getLicensePlate().isEmpty() && !car.getLicensePlate().equals(v.getLicensePlate())) {
                            Optional<Car> license = carRepository.findByLicensePlate(car.getLicensePlate());
                            if (license.isEmpty()) {
                                throw new AppException("License plate already exists", HttpStatus.BAD_REQUEST);
                            } else {
                                v.setLicensePlate(car.getLicensePlate());
                            }
                        }

                        if (car.getYear() > 0 && car.getYear() != v.getYear()) {
                            v.setYear(car.getYear());
                        }

                        return carRepository.save(v);
                    }
                }
            }
        }
        return v;
    }

}
