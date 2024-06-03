package com.challenger.backend.controllers;

import com.challenger.backend.entities.Car;
import com.challenger.backend.services.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(produces = "application/json")
public class CarController {

    CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Car>> allVehicles() {
        return ResponseEntity.ok(carService.allVehicles());
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Car> getVehicle(@PathVariable Long id, @Valid String licensePlate) {
        return ResponseEntity.ok(carService.getVehicle(id, licensePlate));
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Car> createVehicle(@Valid @RequestBody Car car) {
        Car createdCar = carService.saveVehicle(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
        //return ResponseEntity.created(URI.create("/vehicles/" + createdVehicle.getId())).body(createdVehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable @Valid @RequestBody Long id,
                                                @RequestParam @Valid String licensePlate) {
        HttpStatus httpResponse = carService.deleteVehicle(id, licensePlate);
        return ResponseEntity.status(httpResponse).body(
                httpResponse.equals(HttpStatus.NOT_FOUND) ? "Ocorreu um erro no momento da deleçao" : "Veiculo excluido");
    }

    @PatchMapping("/vehicles/{id}")
    public ResponseEntity<Car> updateVehicle(@PathVariable Long id, @Valid @RequestParam String licensePlate,
                                             @Valid @RequestBody Car car) {
        Car updateCar = carService.updateVehicle(id, car, licensePlate);
        return (updateCar == null)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(updateCar)
                : ResponseEntity.status(HttpStatus.OK).body(updateCar);
    }

}
