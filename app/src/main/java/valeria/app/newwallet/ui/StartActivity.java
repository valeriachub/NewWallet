package valeria.app.newwallet.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import valeria.app.newwallet.R;

public class StartActivity extends AppCompatActivity {

    private Unbinder unbinder;

    public static void start(Context context){
        Intent intent = new Intent(context, StartActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_start);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_login)
    void onLoginClicked() {
        LoginActivity.start(this);
        finish();
    }

    @OnClick(R.id.btn_create)
    void OnCreateClicked() {
        RegistrationActivity.start(this);
        finish();
    }
}
