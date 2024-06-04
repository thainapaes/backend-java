package com.challenger.backend.controllers;

import com.challenger.backend.entities.Car;
import com.challenger.backend.services.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> allVehicles() {
        return ResponseEntity.ok(carService.allVehicles());
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getVehicle(@PathVariable Long id, @Valid String licensePlate) {
        return ResponseEntity.ok(carService.getVehicle(id, licensePlate));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/cars")
    public ResponseEntity<Car> createVehicle(@Valid @RequestBody Car car) {
        Car createdCar = carService.saveVehicle(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
        //return ResponseEntity.created(URI.create("/cars/" + createdVehicle.getId())).body(createdVehicle);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<HttpStatusCode> deleteVehicle(@PathVariable @Valid @RequestBody Long id,
                                                        @RequestParam @Valid String licensePlate) {
        HttpStatus httpResponse = carService.deleteVehicle(id, licensePlate);
        if (httpResponse.equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(HttpStatusCode.valueOf(200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatusCode.valueOf(404));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PatchMapping("/cars/{id}")
    public ResponseEntity<Car> updateVehicle(@PathVariable Long id, @Valid @RequestParam String licensePlate,
                                             @Valid @RequestBody Car car) {
        Car updateCar = carService.updateVehicle(id, car, licensePlate);
        return (updateCar == null)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(updateCar)
                : ResponseEntity.status(HttpStatus.OK).body(updateCar);
    }

}
