package valeria.app.newwallet.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import valeria.app.newwallet.R;
import valeria.app.newwallet.databinding.AcRegistrationBinding;
import valeria.app.newwallet.services.ApiClient;
import valeria.app.newwallet.services.model.request.RegisterRequest;

public class RegistrationActivity extends AppCompatActivity {

    private AcRegistrationBinding binding;
    private ProgressDialog progressDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ac_registration);
        binding.btnRegister.setOnClickListener(v -> register());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void register() {
        hideKeyboard(binding.editUsername);
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
        } else if (!isValidEmail(binding.editEmail.getText().toString())) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.editPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @SuppressLint("CheckResult")
    private void registerUser() {
        RegisterRequest request = new RegisterRequest(
                binding.editEmail.getText().toString(),
                binding.editFname.getText().toString(),
                binding.editLname.getText().toString(),
                binding.editPassword.getText().toString(),
                binding.editUsername.getText().toString());

        if (progressDialog == null) progressDialog = new ProgressDialog(this);

        ApiClient.getClient().register(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> progressDialog.show())
                .subscribe(registerResponse -> {
                    progressDialog.dismiss();
                    if (registerResponse != null) {
                        Log.e("app", registerResponse.toString());
                    }
                }, throwable -> {
                    progressDialog.dismiss();
                    throwable.printStackTrace();
                });
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
