package valeria.app.newwallet.ui.model;

public class UserInformation {

    public String email;
    public String firstName;
    public String lastName;
    public String username;
    public String address;
    public String ethBalance;

    public UserInformation(String email, String firstName, String lastName, String username, String address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.address = address;
    }
}
