package valeria.app.newwallet.services.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @Expose
    @SerializedName("email")
    public String email;
    @Expose
    @SerializedName("firstName")
    public String firstName;
    @Expose
    @SerializedName("lastName")
    public String lastName;
    @Expose
    @SerializedName("password")
    public String password;
    @Expose
    @SerializedName("username")
    public String username;

    public RegisterRequest(String email, String firstName, String lastName, String password, String username) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
    }
}
