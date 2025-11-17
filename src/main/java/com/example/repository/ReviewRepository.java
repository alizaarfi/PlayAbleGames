package com.example.repository;

import com.example.domain.Review;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface ReviewRepository extends CrudRepository<Review, Long> {

    // just get all reviews for a given game
    List<Review> findByGameId(Long gameId);
}
