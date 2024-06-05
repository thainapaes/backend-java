package com.challenger.backend.dto;

import com.challenger.backend.entities.Car;

public record CarroPatchDTO(String licensePlate, Car car){
}
