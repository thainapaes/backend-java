package com.challenger.backend.services;

import com.challenger.backend.entities.Car;
import com.challenger.backend.entities.Usuario;
import com.challenger.backend.exceptions.AppException;
import com.challenger.backend.repository.IUsuarioRepository;
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
    IUsuarioRepository usuarioRepository;

    public List<Car> allVehicles() {
        return carRepository.findAll();
    }

    public Car getVehicle(Long id, String licensePlate) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        Car c = null;
        if (usuario.isPresent()) {
            for(Car car : usuario.get().getCarList()) {
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
        Optional<Usuario> clientOptional = usuarioRepository.findById(id);
        if (clientOptional.isPresent()) {
            Usuario usuario = clientOptional.get();
            Car carToRemove = null;
            for (Car c : usuario.getCarList()) {
                if (c.getLicensePlate().equals(licensePlate)) {
                    carToRemove = c;
                    break;
                }
            }

            if (carToRemove != null) {
                usuario.getCarList().remove(carToRemove);
                usuarioRepository.save(usuario); // Salva o usuário atualizado

                carRepository.delete(carToRemove); // Exclui o veículo
                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND; // Veículo não encontrado na lista do usuário
            }
        }

        return HttpStatus.NOT_FOUND;
    }

    public Car updateVehicle(Long id, Car car, String licensePlate) {
        Optional<Usuario> usuarioFound = usuarioRepository.findById(id);
        Car v = null;
        if (usuarioFound.isPresent()) {
            for(Car c : usuarioFound.get().getCarList()) {
                if (c.getLicensePlate().equals(licensePlate)) {
                    Optional<Car> carOptional = carRepository.findByLicensePlate(c.getLicensePlate());
                    if (carOptional.isPresent()) {
                        v = carOptional.get();
                        if (!car.getColor().isEmpty()) {
                            v.setColor(car.getColor());
                        }

                        if (!car.getModel().isEmpty()) {
                            v.setModel(car.getModel());
                        }

                        if (!car.getLicensePlate().isEmpty()) {
                            Optional<Car> license = carRepository.findByLicensePlate(car.getLicensePlate());
                            if (license.isEmpty()) {
                                throw new AppException("License plate already exists", HttpStatus.BAD_REQUEST);
                            } else {
                                v.setLicensePlate(car.getLicensePlate());
                            }
                        }

                        if (car.getYear() > 0) {
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
