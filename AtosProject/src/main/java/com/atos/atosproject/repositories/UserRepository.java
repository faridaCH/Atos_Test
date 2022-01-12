package com.atos.atosproject.repositories;

import com.atos.atosproject.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository  extends CrudRepository<UserEntity, Integer> {
  /*
    Iterable<UserEntity> findByName(String name);
    Iterable<UserEntity> findByNameContains(String name);
    Iterable<UserEntity> findByNameContainsOrCountryContainsIgnoreCase(String name,String Country);
*/
    Iterable<UserEntity> findByNameContainsOrEmailContains(String name,String email);

 // UserEntity findByEmail(String email);

}
