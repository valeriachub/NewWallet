package valeria.app.newwallet.services.model.request;

public class RegisterRequest {

    public String email;
    public String firstName;
    public String lastName;
    public String password;
    public String username;

    public RegisterRequest(String email, String firstName, String lastName, String password, String username) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
    }
}
