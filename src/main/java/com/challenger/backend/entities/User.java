package com.challenger.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Entity
@Getter
@Setter
@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Column(unique=true)
    private String email;
    @NotNull
    @JsonFormat
    private Date birthday;
    @NotNull
    @Column(unique=true)
    private String login;
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotNull
    private String phone;
    private Date createdDate;
    private Date lastLogin;
    @ElementCollection
    private List<Car> carsList;

    public User(String firstName, String lastName, String email, Date birthday, String login, String password,
                String phone, List<Car> carsList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.carsList = carsList;
    }

    public User() {}

}
