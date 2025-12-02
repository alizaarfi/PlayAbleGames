package com.example.repository;

import com.example.domain.UserFavorite;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserFavoriteRepository extends CrudRepository<UserFavorite, Long> {

    List<UserFavorite> findByUserId(Long userId);

    Optional<UserFavorite> findByUserIdAndGameId(Long userId, Long gameId);

    void deleteByUserIdAndGameId(Long userId, Long gameId);
}
