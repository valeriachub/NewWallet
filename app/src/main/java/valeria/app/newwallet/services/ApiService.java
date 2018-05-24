package valeria.app.newwallet.services;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import valeria.app.newwallet.services.model.request.LoginRequest;
import valeria.app.newwallet.services.model.request.RegisterRequest;
import valeria.app.newwallet.services.model.response.LoginResponse;
import valeria.app.newwallet.services.model.response.RegisterResponse;

public interface ApiService {

    @POST("account/api.v.1.0/register")
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @POST("login")
    Observable<LoginResponse> login(@Body LoginRequest request);
}
