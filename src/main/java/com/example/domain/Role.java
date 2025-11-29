package com.example.domain;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.serde.annotation.Serdeable;

@MappedEntity("roles")
@Serdeable
public class Role {
    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @MappedProperty("RoleID")
    private Long roleId;

    @MappedProperty("name")
    private String name;

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}