package valeria.app.newwallet.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import valeria.app.newwallet.R;
import valeria.app.newwallet.data.UserRepository;
import valeria.app.newwallet.services.ApiClient;
import valeria.app.newwallet.services.model.request.LoginRequest;
import valeria.app.newwallet.services.model.response.LoginResponse;
import valeria.app.newwallet.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_username)
    EditText usernameView;
    @BindView(R.id.edit_password)
    EditText passwordView;

    private Unbinder unbinder;
    private ProgressDialog progressDialog;

    private CompositeDisposable compositeDisposable;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        unbinder = ButterKnife.bind(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        compositeDisposable.dispose();
    }

    @OnClick(R.id.btn_login)
    void onLoginClicked() {
        Utils.hideKeyboard(usernameView, this);
        if(!isReady()) return;
        loginUser();
    }

    private boolean isReady() {
        if (usernameView.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordView.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void loginUser(){
        LoginRequest request = new LoginRequest(
                passwordView.getText().toString(),
                usernameView.getText().toString());

        if(progressDialog == null) progressDialog = new ProgressDialog(this);

        Disposable disposable = ApiClient.getClient().login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> progressDialog.show())
                .subscribe(loginResponse -> {
                    progressDialog.dismiss();
                    if(loginResponse!=null){
                        saveUserInfo(loginResponse);
                       showMainScreen();
                    }
                }, throwable -> {
                    progressDialog.dismiss();
                    throwable.printStackTrace();
                });

        compositeDisposable.add(disposable);
    }

    private void saveUserInfo(LoginResponse response){
        UserRepository.getInstance(this).setUserAddress(response.getAddress());
        UserRepository.getInstance(this).setUserUsername(response.getUsername());
        UserRepository.getInstance(this).setUserFName(response.getFirstName());
        UserRepository.getInstance(this).setUserLName(response.getLastName());
        UserRepository.getInstance(this).setUserEmail(response.getEmail());
    }

    private void showMainScreen(){
        MainActivity.start(this);
        finish();
    }
}
