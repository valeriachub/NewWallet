package valeria.app.newwallet.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import valeria.app.newwallet.R;
import valeria.app.newwallet.data.UserRepository;
import valeria.app.newwallet.databinding.AcRegistrationBinding;
import valeria.app.newwallet.services.ApiClient;
import valeria.app.newwallet.services.model.request.RegisterRequest;
import valeria.app.newwallet.services.model.response.RegisterResponse;
import valeria.app.newwallet.utils.Utils;

public class RegistrationActivity extends AppCompatActivity {

    private AcRegistrationBinding binding;
    private ProgressDialog progressDialog;
    private CompositeDisposable compositeDisposable;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ac_registration);
        binding.btnRegister.setOnClickListener(v -> register());
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void register() {
        Utils.hideKeyboard(binding.editUsername, this);
        if (!isReady()) return;
        registerUser();
    }

    private boolean isReady() {
        if (binding.editUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.editFname.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.editLname.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.isValidEmail(binding.editEmail.getText().toString())) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.editPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void registerUser() {
        RegisterRequest request = new RegisterRequest(
                binding.editEmail.getText().toString(),
                binding.editFname.getText().toString(),
                binding.editLname.getText().toString(),
                binding.editPassword.getText().toString(),
                binding.editUsername.getText().toString());

        if (progressDialog == null) progressDialog = new ProgressDialog(this);

        Disposable disposable = ApiClient.getClient().register(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> progressDialog.show())
                .subscribe(registerResponse -> {
                    progressDialog.dismiss();
                    if (registerResponse != null) {
                        saveUserInfo(registerResponse);
                        showMainScreen();
                    }
                }, throwable -> {
                    progressDialog.dismiss();
                    throwable.printStackTrace();
                });

        compositeDisposable.add(disposable);
    }

    private void saveUserInfo(RegisterResponse response) {
        UserRepository.getInstance(this).setUserAddress(response.getAddress());
        UserRepository.getInstance(this).setUserUsername(response.getUsername());
        UserRepository.getInstance(this).setUserFName(response.getFirstName());
        UserRepository.getInstance(this).setUserLName(response.getLastName());
        UserRepository.getInstance(this).setUserEmail(response.getEmail());
    }

    private void showMainScreen() {
        MainActivity.start(this);
        finish();
    }
}
