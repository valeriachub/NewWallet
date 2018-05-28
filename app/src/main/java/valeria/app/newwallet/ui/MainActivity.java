package valeria.app.newwallet.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import valeria.app.newwallet.databinding.AcMainBinding;
import valeria.app.newwallet.services.ApiClient;
import valeria.app.newwallet.ui.model.UserInformation;
import valeria.app.newwallet.utils.Const;
import valeria.app.newwallet.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_address)
    EditText addressView;
    @BindView(R.id.edit_value)
    EditText valueView;

    private AcMainBinding binding;
    private CompositeDisposable compositeDisposable;
    private UserInformation userInformation;
    private Unbinder unbinder;
    private ProgressDialog progressDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ac_main);
        compositeDisposable = new CompositeDisposable();
        unbinder = ButterKnife.bind(this);
        initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        unbinder.unbind();
    }

    private void initUI() {
        userInformation = getUserInfo();
        binding.setUserInfo(userInformation);
        getBalances();
    }

    private UserInformation getUserInfo() {
        return new UserInformation(
                UserRepository.getInstance(this).getUserEmail(),
                UserRepository.getInstance(this).getUserFName(),
                UserRepository.getInstance(this).getUserLName(),
                UserRepository.getInstance(this).getUserUsername(),
                UserRepository.getInstance(this).getUserAddress());
    }

    private void getEthBalance() {
        Disposable disposable = ApiClient.getClient().getEthBalance(UserRepository.getInstance(this).getUserAddress())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> userInformation.ethBalance.set(result),
                        Throwable::printStackTrace);

        compositeDisposable.add(disposable);
    }

    private void getTokenBalance() {
        Disposable disposable = ApiClient.getClient().getTokenBalance(UserRepository.getInstance(this).getUserAuthToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> userInformation.tokenBalance.set(result),
                        Throwable::printStackTrace);

        compositeDisposable.add(disposable);
    }

    @OnClick({R.id.text_ether, R.id.text_ninja})
    void onAmountClicked() {
        userInformation.isEthSelected.set(!userInformation.isEthSelected.get());
    }

    @OnClick(R.id.btn_send)
    void onSendClicked() {
        Utils.hideKeyboard(addressView, this);
        if (!isReady()) return;
        if (userInformation.isEthSelected.get()) {
            sendEth();
        } else {
            sendToken();
        }
    }

    private void sendEth() {
        if (progressDialog == null) progressDialog = new ProgressDialog(this);

        Disposable disposable = ApiClient.getClient().sendEth("0x2b4beddc9a4b81e2a103f9087c8f1164f4a88a3c", Integer.valueOf(valueView.getText().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> progressDialog.show())
                .subscribe(
                        result -> {
                            progressDialog.dismiss();
                            clearFields();
                            getBalances();
                        },
                        throwable -> {
                            progressDialog.dismiss();
                            getBalances();
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            throwable.printStackTrace();
                        });

        compositeDisposable.add(disposable);
    }

    private void sendToken() {
        if (progressDialog == null) progressDialog = new ProgressDialog(this);

        Disposable disposable = ApiClient.getClient().sendToken(
                UserRepository.getInstance(this).getUserAuthToken(),
                "0x2b4beddc9a4b81e2a103f9087c8f1164f4a88a3c",
                Integer.valueOf(valueView.getText().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> progressDialog.show())
                .subscribe(
                        result -> {
                            progressDialog.dismiss();
                            clearFields();
                            getBalances();
                        },
                        throwable -> {
                            progressDialog.dismiss();
                            getBalances();
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            throwable.printStackTrace();
                        });

        compositeDisposable.add(disposable);
    }

    private boolean isReady() {
        if (addressView.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        } else if (valueView.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void getBalances(){
        getTokenBalance();
        getEthBalance();
    }

    private void clearFields() {
        addressView.setText("");
        valueView.setText("");
    }
}
