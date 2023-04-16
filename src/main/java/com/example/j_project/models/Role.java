package com.example.j_project.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Role {

    @Id
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "id='" + name + '\'' +
                '}';
    }

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void setName(String id) {
        this.name = id;
    }
}
