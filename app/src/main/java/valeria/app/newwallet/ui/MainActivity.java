package valeria.app.newwallet.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
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

public class MainActivity extends AppCompatActivity {

    private AcMainBinding binding;
    private CompositeDisposable compositeDisposable;
    private UserInformation userInformation;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ac_main);
        compositeDisposable = new CompositeDisposable();

        initUI();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void initUI() {
        userInformation = getUserInfo();
        binding.setUserInfo(userInformation);
        getEthBalance();
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

}
