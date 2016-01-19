package com.tele2.models.dao.security;

import com.tele2.models.dto.security.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dell on 16.01.2016.
 */

@Transactional
public interface ResourcesDAO extends CrudRepository<Resource, Long> {

    public Resource findByName(String name);

}
