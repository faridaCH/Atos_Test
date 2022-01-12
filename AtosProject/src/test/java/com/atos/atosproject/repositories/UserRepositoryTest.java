package com.atos.atosproject.repositories;

import com.atos.atosproject.entities.Gender;
import com.atos.atosproject.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

// use a Spring context
@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired
 private UserRepository userRepoUnderTest;

    @Test
    void findByNameContainsOrEmailContains() {
        //given
       UserEntity user=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321",Gender.Woman,"farida@gmail.com","1234");

        userRepoUnderTest.save(user);
        // when
        Iterable<UserEntity> expected= userRepoUnderTest.findByNameContainsOrEmailContains("farida@gmail.com", "farida@gmail.com");
        // then
        assertEquals(true, expected.iterator().hasNext());
        System.out.println(expected.iterator().hasNext());
        AtomicReference<Boolean> result= new AtomicReference<>(false);
        expected.forEach(userEntity -> result.set(userEntity.getName().equals("farida") && userEntity.getEmail().equals("farida@gmail.com") ));
       assertTrue(result.get());


    }
}