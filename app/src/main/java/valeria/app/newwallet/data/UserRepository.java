package valeria.app.newwallet.data;

import android.content.Context;

import valeria.app.newwallet.utils.PreferencesManager;

public class UserRepository {

    private PreferencesManager preferenceManager;

    private static UserRepository userRepository;

    public static UserRepository getInstance(Context context){
        if(userRepository == null) userRepository = new UserRepository(context);
        return userRepository;
    }

    private UserRepository(Context context) {
        this.preferenceManager = PreferencesManager.getInstance(context);
    }

    public void setUserAddress(String address){
        preferenceManager.setUserAddress(address);
    }

    public String getUserAddress(){
        return preferenceManager.getUserAddress();
    }

    public void setUserUsername(String username){
        preferenceManager.setUserUsername(username);
    }

    public String getUserUsername(){
        return preferenceManager.getUserUsername();
    }

    public void setUserFName(String fname){
        preferenceManager.setUserFName(fname);
    }

    public String getUserFName(){
        return preferenceManager.getUserFName();
    }

    public void setUserLName(String lname){
        preferenceManager.setUserLName(lname);
    }

    public String getUserLName(){
        return preferenceManager.getUserLName();
    }

    public void setUserEmail(String email){
        preferenceManager.setUserEmail(email);
    }

    public String getUserEmail(){
        return preferenceManager.getUserEmail();
    }

    public void setUserAuthToken(String token){
        preferenceManager.setUserAuthToken(token);
    }

    public String getUserAuthToken(){
        return preferenceManager.getUserAuthToken();
    }

}
