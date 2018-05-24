package valeria.app.newwallet.services.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @Expose
    @SerializedName("password")
    public String password;
    @Expose
    @SerializedName("username")
    public String username;

    public LoginRequest(String password, String username) {
        this.password = password;
        this.username = username;
    }
}
