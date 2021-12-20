package com.atos.atosproject.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "USER", schema = "PUBLIC", catalog = "ATOS_DB")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private int id;
    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;
    @Basic
    @Column(name = "BIRTHDATE", nullable = false)
    private Date birthdate;
    @Basic
    @Column(name = "COUNTRY", nullable = false)
    private String country;
    @Basic
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Basic
    @Column(name = "GENDER")
    private Gender gender;
    @Basic
    @Column(name = "EMAIL")
    private String email;
    @Basic
    @Column(name = "PASSWORD")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(birthdate, that.birthdate) && Objects.equals(country, that.country) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(gender, that.gender) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate, country, phoneNumber, gender, email, password);
    }
}
