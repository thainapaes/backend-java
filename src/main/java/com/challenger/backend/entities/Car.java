package com.challenger.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Entity
@Getter
@Setter
@Data
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Configuração para auto incrementar o ID
    @JsonIgnore
    private Long id;
    @NotNull
    private String model;
    @NotNull
    @Column(unique=true)
    private String licensePlate;
    @NotNull
    private String color;
    @NotNull
    @Column(name = "vehicle_year")
    private int year;

    public Car(String model, String licensePlate, String color, int year) {
        this.model = model;
        this.licensePlate = licensePlate;
        this.color = color;
        this.year = year;
    }

    public Car() {}

}
