package com.maad.bankticket;

public class Client {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Client() {
    } //We use this to read from firebase

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
