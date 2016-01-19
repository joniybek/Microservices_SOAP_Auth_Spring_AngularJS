package com.tele2.models.dao.security;

import com.tele2.models.dto.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dell on 16.01.2016.
 */
@Transactional
public interface UsersDAO extends CrudRepository<User,Long> {
    public User findByEmail(String email);
    public User findByUsername(String username);

}
