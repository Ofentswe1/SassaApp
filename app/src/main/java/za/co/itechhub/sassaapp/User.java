package za.co.itechhub.sassaapp;
import java.io.Serializable;

/**
 * Created by lero on 2018/04/09.
 */

public class User implements Serializable {
    String name;
    String surname;
    String address;
    String username;

    public User(String name, String surname, String address, String username) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.username = username;
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