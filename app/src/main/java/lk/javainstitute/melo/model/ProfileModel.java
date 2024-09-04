package lk.javainstitute.melo.model;

public class ProfileModel {

    String username;
    String address;
    String number;
    String password;
    String  email;

    public ProfileModel(String username, String address, String number, String password, String email) {
        this.username = username;
        this.address = address;
        this.number = number;
        this.password = password;
        this.email = email;
    }

    public ProfileModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
