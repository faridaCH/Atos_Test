package com.atos.atosproject.api;

import com.atos.atosproject.entities.Gender;
import com.atos.atosproject.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
class UserAPIControllerIT {



    @Autowired
    private WebTestClient webTestClient; // available with Spring WebFlux

    @Autowired
    private TestRestTemplate testRestTemplate; // available with Spring Web MVC

    @LocalServerPort
    private Integer port;


    HttpHeaders headers = new HttpHeaders();

    @Test
    void httpClientExample() {

        // no need for this
       WebClient webClient = WebClient.builder()
               .baseUrl("http://localhost:" + port)
                .build();

        this.webTestClient
                .get()
                .uri("/api/user")
                .exchange()
                .expectStatus().is2xxSuccessful();

    }




    @Test
    void getAll() {


            HttpEntity entity = new HttpEntity<>(null, headers);

        ResponseEntity responseEntity = testRestTemplate.exchange("http://localhost:8080/api/user", HttpMethod.GET, entity, String.class);
            assertNotNull(responseEntity);


    }

    @Test
    void getAllBykeyWord() {

        HttpEntity entity = new HttpEntity<>(null, headers);

        ResponseEntity responseEntity = testRestTemplate.exchange("http://localhost:8080/api/user/?search=jad", HttpMethod.GET, entity, String.class);

        assertTrue(responseEntity.getStatusCode().toString().equals("200 OK"));

    }

    @Test
    void get() {
        HttpEntity entity = new HttpEntity<>(null, headers);

        ResponseEntity responseEntity = testRestTemplate.exchange("http://localhost:8080/api/user/1", HttpMethod.GET, entity, String.class);

        assertTrue(responseEntity.getStatusCode().toString().equals("200 OK"));
        assertNotEquals(responseEntity.getBody().toString(),"[]");
    }

    @Test
    void add() {
        UserEntity user=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Woman,"farida@gmail.com","1234");
        ResponseEntity<String> responseEntity = this.testRestTemplate.postForEntity("http://localhost:8080/api/user",user,String.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    void update() {

// create user
        UserEntity user=new UserEntity("farida", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Woman,"farida@gmail.com","1234");
        HttpEntity entity = new HttpEntity<>(user, headers);
        // add user
        ResponseEntity responseEntity = testRestTemplate.exchange("http://localhost:8080/api/user", HttpMethod.POST, entity, String.class);
        String id = responseEntity.toString().substring(responseEntity.toString().indexOf(":")+1, responseEntity.toString().indexOf(",\""));


        UserEntity userEdit=new UserEntity("faridaEdit", Date.valueOf("2000-12-12"),"France","0987654321", Gender.Woman,"faridaedit@gmail.com","1234");
        HttpEntity entityedit = new HttpEntity<>(userEdit, headers);
        // get user
        ResponseEntity responseEntityEdit = testRestTemplate.exchange("http://localhost:8080/api/user/"+id, HttpMethod.PUT, entityedit, String.class);
        System.out.println("*******************************"+ responseEntityEdit  +"**************************");
        System.out.println(testRestTemplate.exchange("http://localhost:8080/api/user/"+id, HttpMethod.GET, entityedit, String.class));

        assertEquals(200, responseEntityEdit.getStatusCodeValue());
    }

    @Test
    void delete() {
        // create user
        UserEntity user = new UserEntity("farida", Date.valueOf("2000-12-12"), "France", "0987654321", Gender.Woman, "farida@gmail.com", "1234");
        HttpEntity entityadd = new HttpEntity<>(user, headers);
        // add user
        ResponseEntity responseEntityAdd = testRestTemplate.exchange("http://localhost:8080/api/user", HttpMethod.POST, entityadd, String.class);
        System.out.println(responseEntityAdd);
        String id = responseEntityAdd.toString().substring(responseEntityAdd.toString().indexOf(":")+1, responseEntityAdd.toString().indexOf(",\""));
        System.out.println(id);
        // delete user
        HttpEntity entitydelete = new HttpEntity<>(null, headers);
        ResponseEntity responseEntityDelete = testRestTemplate.exchange("http://localhost:8080/api/user/" + id, HttpMethod.DELETE, entitydelete, String.class);
        System.out.println(responseEntityDelete);
        // after delelted user
        assertEquals(200, responseEntityDelete.getStatusCodeValue());
    }

}