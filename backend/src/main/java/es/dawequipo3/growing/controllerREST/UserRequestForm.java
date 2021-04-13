package es.dawequipo3.growing.controllerREST;

public class UserRequestForm {
    private String email;
    private String username;
    private String name;
    private String surname;
    private String encodedPassword;
    private String confirmEncodedPassword;

    public UserRequestForm(String email, String username, String name, String surname, String encodedPassword, String confirmEncodedPassword) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.encodedPassword = encodedPassword;
        this.confirmEncodedPassword = confirmEncodedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public String getConfirmEncodedPassword() {
        return confirmEncodedPassword;
    }

    public void setConfirmEncodedPassword(String confirmEncodedPassword) {
        this.confirmEncodedPassword = confirmEncodedPassword;
    }
}
