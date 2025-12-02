package com.example.repository;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import com.example.domain.Blog;

import java.util.List;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface BlogRepository extends CrudRepository<Blog, Long> {

    @Query("SELECT * FROM blog ORDER BY DateCreated DESC")
    List<Blog> findAllOrderByDateCreatedDesc();
}