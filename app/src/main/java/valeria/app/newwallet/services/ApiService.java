package valeria.app.newwallet.services;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import valeria.app.newwallet.services.model.request.LoginRequest;
import valeria.app.newwallet.services.model.request.RegisterRequest;
import valeria.app.newwallet.services.model.response.LoginResponse;
import valeria.app.newwallet.services.model.response.RegisterResponse;
import valeria.app.newwallet.services.model.response.SendEthResponse;

public interface ApiService {

    //Account Controller

    @POST("account/api.v.1.0/register")
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @POST("login")
    Observable<LoginResponse> login(@Body LoginRequest request);



    //Ethereum Controller

    @GET
    Observable<String> getEthBalance(@Url String url);

    @GET
    Observable<SendEthResponse> sendEth(@Url String url);


}
