package com.challenger.backend.controllers;

import com.challenger.backend.dto.CarroPatchDTO;
import com.challenger.backend.dto.CarroPostDTO;
import com.challenger.backend.entities.Car;
import com.challenger.backend.services.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/api", produces = "application/json")
public class CarController {

    CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> allVehicles() {
        return ResponseEntity.ok(carService.allCars());
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<List<Car>> getCar(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCar(id));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/cars")
    public ResponseEntity<Car> createVehicle(@RequestBody CarroPostDTO body) {
        Car createdCar = carService.saveCarWithUser(body.car(), body.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<HttpStatusCode> deleteVehicle(@PathVariable @Valid @RequestBody Long id,
                                                        @RequestParam @Valid String licensePlate) {
        HttpStatus httpResponse = carService.deleteCar(id, licensePlate);
        if (httpResponse.equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(HttpStatusCode.valueOf(200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatusCode.valueOf(404));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PatchMapping("/cars/{id}")
    public ResponseEntity<Car> updateVehicle(@PathVariable Long id, @RequestBody CarroPatchDTO body) {
        Car updateCar = carService.updateCar(id, body.car() , body.licensePlate());
        return (updateCar == null)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(updateCar)
                : ResponseEntity.status(HttpStatus.OK).body(updateCar);
    }

}
