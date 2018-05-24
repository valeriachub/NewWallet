package valeria.app.newwallet.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import valeria.app.newwallet.R;
import valeria.app.newwallet.data.UserRepository;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash);
        startApp();
    }

    private void startApp(){
        boolean isUserExist = !UserRepository.getInstance(this).getUserAddress().isEmpty();

        if(isUserExist){
            MainActivity.start(this);
        }else{
            StartActivity.start(this);
        }
    }
}
