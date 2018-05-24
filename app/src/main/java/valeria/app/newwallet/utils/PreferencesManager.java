package valeria.app.newwallet.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

    private static PreferencesManager preferencesManager;
    private static SharedPreferences preferences;
    private Context context;

    public static PreferencesManager getInstance(Context context){
        if(preferencesManager == null) preferencesManager = new PreferencesManager(context);
        return preferencesManager;
    }

    private PreferencesManager(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUserAddress(String address){
       SharedPreferences.Editor editor = preferences.edit();
       editor.putString(Const.PREF_ADDRESS, address);
       editor.apply();
    }

    public String getUserAddress(){
       return preferences.getString(Const.PREF_ADDRESS, "");
    }

    public void setUserUsername(String username){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Const.PREF_USERNAME, username);
        editor.apply();
    }

    public String getUserUsername(){
        return preferences.getString(Const.PREF_USERNAME, "");
    }

    public void setUserFName(String fname){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Const.PREF_FNAME, fname);
        editor.apply();
    }

    public String getUserFName(){
        return preferences.getString(Const.PREF_FNAME, "");
    }

    public void setUserLName(String lname){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Const.PREF_LNAME, lname);
        editor.apply();
    }

    public String getUserLName(){
        return preferences.getString(Const.PREF_LNAME, "");
    }

    public void setUserEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Const.PREF_EMAIL, email);
        editor.apply();
    }

    public String getUserEmail(){
        return preferences.getString(Const.PREF_EMAIL, "");
    }
}
