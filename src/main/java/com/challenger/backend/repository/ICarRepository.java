package com.challenger.backend.repository;

import com.challenger.backend.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICarRepository extends JpaRepository<Car, Long> {
    public Optional<Car> findByLicensePlate(String licensePlate);
}
