package com.example.repository;

import com.example.domain.Reply;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.annotation.Query;
import java.util.List;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface ReplyRepository extends CrudRepository<Reply, Long> {

    @Query(value = "SELECT r.*, u.Username as username FROM replies r LEFT JOIN users u ON r.UserID = u.UserID WHERE r.DiscussionID = :discussionID ORDER BY r.DateCreated ASC",
            nativeQuery = true)
    List<Reply> findByDiscussionIDOrderByDateCreatedAsc(Long discussionID);
}