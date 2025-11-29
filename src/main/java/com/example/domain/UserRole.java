package com.example.domain;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.serde.annotation.Serdeable;

@MappedEntity("user_roles")
@Serdeable
public class UserRole {
    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @MappedProperty("id")
    private Long id;

    @MappedProperty("UserID")
    private Long userId;

    @MappedProperty("RoleID")
    private Long roleId;

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getRoleId() { return roleId; }
}