package com.example.repository;

import com.example.domain.GalleryImage;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface GalleryRepository extends CrudRepository<GalleryImage, Long> {
//    Returns all gallery images associated with the given game.
    List<GalleryImage> findByGameId(Long gameId);
}
