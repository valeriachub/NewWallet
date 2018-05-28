package valeria.app.newwallet.ui.model;

import android.databinding.ObservableField;

public class UserInformation {

    public String email;
    public String firstName;
    public String lastName;
    public String username;
    public String address;

    public final ObservableField<Boolean> isEthSelected = new ObservableField<>(false);
    public final ObservableField<String> ethBalance = new ObservableField<>();
    public final ObservableField<String> tokenBalance = new ObservableField<>();

    public UserInformation(String email, String firstName, String lastName, String username, String address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
