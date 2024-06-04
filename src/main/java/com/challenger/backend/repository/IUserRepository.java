package com.challenger.backend.repository;

import com.challenger.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByLogin(String login);
}
