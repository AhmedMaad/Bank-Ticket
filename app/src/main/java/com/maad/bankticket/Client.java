package com.maad.bankticket;

public class Client {

    private String firstName;
    private String lastName;
    private String email;

    public Client() {
    } //We use this to read from firebase

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

}
