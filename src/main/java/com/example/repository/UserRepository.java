package com.example.repository;

import com.example.domain.User;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserRepository extends CrudRepository<User, Long>{
        Optional<User> findByUsername(String username);
        Optional<User> findByEmail(String email);

        //pasword update only
        @Query("UPDATE users SET EncryptedPassword =:password WHERE UserID= :id")
        void updatePassword(long id, String password);
}


