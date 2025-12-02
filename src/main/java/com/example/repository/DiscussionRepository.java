package com.example.repository;

import com.example.domain.Discussion;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.annotation.Query;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface DiscussionRepository extends CrudRepository<Discussion, Long> {

    @Query(value = "SELECT d.*, u.Username as username FROM discussions d LEFT JOIN users u ON d.UserID = u.UserID ORDER BY d.DateCreated DESC",
            nativeQuery = true)
    List<Discussion> findAllOrderByDateCreatedDesc();

    @Query(value = "SELECT d.*, u.Username as username FROM discussions d LEFT JOIN users u ON d.UserID = u.UserID WHERE d.DiscussionID = :id",
            nativeQuery = true)
    @Override
    Optional<Discussion> findById(Long id);
}