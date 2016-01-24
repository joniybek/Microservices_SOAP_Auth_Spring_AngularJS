package com.tele2.models.dao;

import com.tele2.models.dto.Environment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dell on 16.01.2016.
 */
@Transactional
public interface EnvironmentDAO extends CrudRepository<Environment, Long> {
    public Environment findByName(String name);


}
