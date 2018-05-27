package za.co.itechhub.sassaapp.models;

import java.io.Serializable;

/**
 * Created by lero on 2018/04/09.
 */

public class User implements Serializable {
    String name;
    String surname;
    String address;
    String username;
    String password;

    public User() {
    }

    public User(String name, String surname, String address, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.username = username;
        this.password = password;

    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}