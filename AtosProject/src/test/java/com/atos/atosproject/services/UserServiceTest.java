package com.atos.atosproject.services;

import com.atos.atosproject.entities.Gender;
import com.atos.atosproject.entities.UserEntity;

import org.junit.jupiter.api.Test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.persistence.EntityNotFoundException;
import java.io.InvalidObjectException;
import java.sql.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

// use a Spring context
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService underTest;


    @Test
    void findAll() throws InvalidObjectException {
        //
        Iterable<UserEntity>  usersBefore= underTest.findAll();
        AtomicInteger nbBeforeAdd= new AtomicInteger();
        usersBefore.forEach(userEntity -> nbBeforeAdd.getAndIncrement());


        //
        UserEntity user=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Woman,"farida@gmail.com","1234");
        underTest.addUser(user);

        //
        Iterable<UserEntity>  usersAfter= underTest.findAll();
        AtomicInteger nbAfterAdd= new AtomicInteger();
        usersAfter.forEach(userEntity -> nbAfterAdd.getAndIncrement());
        assertEquals(nbAfterAdd.intValue(),nbBeforeAdd.intValue()+1);
    }

    @Test
    void testFindAll() throws InvalidObjectException {
        //
        UserEntity user=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Woman,"farida1@gmail.com","1234");
        underTest.addUser(user);
        Iterable<UserEntity>  usersBefore= underTest.findAll("farida");
        AtomicInteger nbBeforeAdd= new AtomicInteger();
        usersBefore.forEach(userEntity -> nbBeforeAdd.getAndIncrement());


        //
        UserEntity user2=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Woman,"farida2@gmail.com","1234");
        underTest.addUser(user2);

        //
        Iterable<UserEntity>  usersAfter= underTest.findAll("farida");
        AtomicInteger nbAfterAdd= new AtomicInteger();
        usersAfter.forEach(userEntity -> nbAfterAdd.getAndIncrement());
        assertEquals(nbAfterAdd.intValue(),nbBeforeAdd.intValue()+1);
        assertEquals(nbAfterAdd.intValue(),2);
    }

    @Test
    void findUser() throws InvalidObjectException {
        UserEntity user=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Woman,"farida@gmail.com","1234");
        underTest.addUser(user);
        //
        AtomicInteger idSavedUser= new AtomicInteger();
        underTest.findAll("farida@gmail.com").iterator().forEachRemaining(userEntity -> idSavedUser.set(userEntity.getId()));
        UserEntity user1=underTest.findUser(idSavedUser.intValue());
        //
        assertEquals(user.getId(),user1.getId());
        assertEquals(user.getName(),user1.getName());
        assertEquals(user.getBirthdate(),user1.getBirthdate());
        assertEquals(user.getGender(),user1.getGender());
        assertEquals(user.getEmail(),user1.getEmail());

    }

    @Test
    void validateEmail() {
        UserEntity user=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Woman,"faridagmail.com","1234");
        InvalidObjectException expectedException=assertThrows(InvalidObjectException.class ,()->underTest.addUser(user));
     assertEquals( "Invalid Email", expectedException.getMessage());
    }

    @Test
    void getAdultAge() {
        UserEntity user=new UserEntity("farida", Date.valueOf("2018-12-12"),"France","0987654321", Gender.Woman,"farida@gmail.com","1234");
        // first check
        InvalidObjectException expectedException=assertThrows(InvalidObjectException.class ,()->underTest.addUser(user));

        assertEquals( " You aren't allowed to create an account ", expectedException.getMessage());
        // second check
        UserEntity user1=new UserEntity("farida", Date.valueOf("2022-12-12"),"France","0987654321", Gender.Woman,"farida@gmail.com","1234");

        InvalidObjectException expectedException1=assertThrows(InvalidObjectException.class ,()->underTest.addUser(user1));

        assertEquals( " Invalid  Birthdate ", expectedException1.getMessage());
    }

    @Test
    void validatePhoneNumber() {
        UserEntity user=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654F", Gender.Woman,"farida@gmail.com","1234");
        // first check
        InvalidObjectException expectedException=assertThrows(InvalidObjectException.class ,()->underTest.addUser(user));

        assertEquals( "Invalid  PhoneNumber", expectedException.getMessage());
    }

    @Test
    void addUserWithSuccess() throws InvalidObjectException {

       UserEntity user=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Woman,"farida@gmail.com","1234");
       underTest.addUser(user);

        Iterable<UserEntity>  savedUser= underTest.findAll("farida@gmail.com");

        assertTrue(savedUser.iterator().hasNext());
        System.out.println(savedUser.iterator().hasNext());
        AtomicReference<Boolean> result= new AtomicReference<>(false);
        // name test
        savedUser.forEach(userEntity -> result.set(userEntity.getName().equals("farida")));
        assertTrue(result.get());
        // birthdate test
        savedUser.forEach(userEntity -> result.set(userEntity.getBirthdate().equals(Date.valueOf("2000-12-12"))));
        assertTrue(result.get());
        // country test
        savedUser.forEach(userEntity -> result.set(userEntity.getCountry().equals("France")));
        assertTrue(result.get());

    }

    @Test
    void editUserWithSuccess() throws InvalidObjectException {
        UserEntity user=new UserEntity("faridachel", Date.valueOf("2000-10-10"),"France","0987654321", Gender.Man,"farida33@gmail.com","1234");

        underTest.addUser(user);

        Iterable<UserEntity>  savedUser= underTest.findAll("farida33@gmail.com");
        AtomicInteger idSavedUser= new AtomicInteger();
        savedUser.forEach(userEntity -> idSavedUser.set(userEntity.getId()));

        underTest.editUser(idSavedUser.intValue(),new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Man,"farida@gmail.com","1234"));

        Iterable<UserEntity>  editedUser= underTest.findAll("farida@gmail.com");
        assertTrue(editedUser.iterator().hasNext());
        AtomicInteger idEditUser= new AtomicInteger();
        savedUser.forEach(userEntity -> idEditUser.set(userEntity.getId()));
        assertNotNull(idEditUser);
        assertEquals(idEditUser.intValue(),idSavedUser.intValue());

        assertEquals(underTest.findUser(idEditUser.intValue()),underTest.findUser(idSavedUser.intValue()));
        assertEquals(underTest.findUser(idEditUser.intValue()).getName(),underTest.findUser(idSavedUser.intValue()).getName());
        assertEquals(underTest.findUser(idEditUser.intValue()).getEmail(),underTest.findUser(idSavedUser.intValue()).getEmail());
        assertEquals(underTest.findUser(idEditUser.intValue()).getCountry(),underTest.findUser(idSavedUser.intValue()).getCountry());
        assertEquals(underTest.findUser(idEditUser.intValue()).getBirthdate(),underTest.findUser(idSavedUser.intValue()).getBirthdate());
    }

    @Test
    void delete() throws InvalidObjectException {
        UserEntity user=new UserEntity("farida", Date.valueOf("2000-10-10"),"France","0987654321", Gender.Man,"farida@gmail.com","1234");

        underTest.addUser(user);
        AtomicInteger idSavedUser= new AtomicInteger();
       underTest.findAll("farida@gmail.com").iterator().forEachRemaining(userEntity -> idSavedUser.set(userEntity.getId()));
      underTest.delete(idSavedUser.intValue());

        NoSuchElementException exept=assertThrows(NoSuchElementException.class ,()->underTest.findUser(idSavedUser.intValue()));
        assertEquals("No value present",exept.getMessage());

    }

    @Test
    void findUserEntityNotFoundException() {

       Exception expectedException=assertThrows(Exception.class ,()->underTest.findUser(0));
        assertEquals( NoSuchElementException.class.getName(), expectedException.getClass().getName());
        assertEquals( "No value present", expectedException.getMessage());
    }
}