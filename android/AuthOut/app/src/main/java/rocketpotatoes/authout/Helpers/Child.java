package rocketpotatoes.authout.Helpers;


import java.io.Serializable;

public class Child implements Serializable {
    private final String firstName;
    private final String surname;
    private final String status;

    public Child(String firstName, String surname, String status) {
        this.firstName = firstName;
        this.surname = surname;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getStatus() {
        return status;
    }
}
