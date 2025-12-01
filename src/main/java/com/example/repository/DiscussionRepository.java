package com.example.repository;

import com.example.domain.Discussion;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface DiscussionRepository extends CrudRepository<Discussion, Long> {
    List<Discussion> findAllOrderByDateCreatedDesc();
}