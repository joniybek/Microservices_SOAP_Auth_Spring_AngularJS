package com.tele2.models.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ENVIRONMENT", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotNull
    String name;

    @NotNull
    String countryName;

    @NotNull
    String countryCode;

    @NotNull
    String endpoint;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Environment(String name, String countryName, String countryCode, String endpoint) {
        this.name = name;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.endpoint = endpoint;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountryCode() {

        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Environment() {

    }
}
