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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
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
    @JsonIgnore
    //@JsonView(Views.DetailedView.class)
    private Date createdDate;
    @JsonIgnore
    //@JsonView(Views.DetailedView.class)
    private Date lastLogin;
    @ElementCollection
    private List<Car> carList;

    public Usuario(String firstName, String lastName, String email, Date birthday, String login, String password,
                   String phone, List<Car> carList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.carList = carList;
    }

    public Usuario() {}

}
