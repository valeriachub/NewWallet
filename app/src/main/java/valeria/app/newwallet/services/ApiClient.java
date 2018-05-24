package valeria.app.newwallet.services;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import valeria.app.newwallet.services.model.request.LoginRequest;
import valeria.app.newwallet.services.model.request.RegisterRequest;
import valeria.app.newwallet.services.model.response.LoginResponse;
import valeria.app.newwallet.services.model.response.RegisterResponse;

public class ApiClient implements ApiService {

    private static ApiClient client = null;
    private static ApiService api;

    private static String BASE_URL = "http://91.234.37.244:8080/nynja/";

    public static ApiClient getClient() {
        if (client == null) initClient();
        return client;
    }

    private static void initClient() {
        client = new ApiClient();

        Retrofit retorfit  = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retorfit.create(ApiService.class);
    }

    @Override
    public Observable<RegisterResponse> register(RegisterRequest request) {
        return api.register(request);
    }

    @Override
    public Observable<LoginResponse> login(LoginRequest request) {
        return api.login(request);
    }


}
