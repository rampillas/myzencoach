package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.util.Log;

import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.ApiCallsForLogin;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RetrofitClient;
import com.unir.grupo2.myzeancoach.domain.model.User;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.CreateUserActivity;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.unir.grupo2.myzeancoach.domain.utils.Constants.URL_SERVER;

/**
 * Created by andres on 28/02/2017.
 */

public class CreateUserServer {

    RetrofitClient conexionAPIretrofit = new RetrofitClient();
    Retrofit retrofit = conexionAPIretrofit.getClient(URL_SERVER);
    ApiCallsForLogin conexioAPI = retrofit.create(ApiCallsForLogin.class);

    public void newUser(String usuarioValor, String emailValor, String nombreValor, String apellidoValor,
                        String passwordValor, String nacimientoValor, String sexoValor, String paisValor,
                        String ciudadValor, String descripcionValor, String zonaValor, String cambioPaisValor,
                        String estudiosValor, String notificationToken, final CreateUserActivity createUserActivity) {

        // RxJava
        conexioAPI.createUser(usuarioValor, emailValor, nombreValor, apellidoValor, passwordValor, nacimientoValor,
                sexoValor, paisValor, ciudadValor, descripcionValor, zonaValor, cambioPaisValor, estudiosValor, notificationToken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Create process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Create process", "error " + e);
                        createUserActivity.userExits();
                        createUserActivity.showContent();
                    }

                    @Override
                    public void onNext(User userObject) {
                        Log.d("Create process", "ok creado");
                        createUserActivity.finishCreateUser(userObject.getUsername());
                        createUserActivity.showContent();
                    }
                });
    }
}
