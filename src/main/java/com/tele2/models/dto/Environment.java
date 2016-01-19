package com.tele2.models.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Dell on 16.01.2016.
 */
@Entity
public class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

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

    public Environment(String countryName, String countryCode, String endpoint) {
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


}
