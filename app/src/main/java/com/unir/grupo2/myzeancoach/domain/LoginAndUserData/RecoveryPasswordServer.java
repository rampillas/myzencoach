package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.util.Log;

import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.ApiCallsForLogin;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RecoveryPasswordObject;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RetrofitClient;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.RecoveryPasswordActivity;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.unir.grupo2.myzeancoach.domain.utils.Constants.URL_SERVER;

/**
 * Created by andres on 28/02/2017.
 */

public class RecoveryPasswordServer {
    RetrofitClient apiRetrofitConexion = new RetrofitClient();
    Retrofit retrofit = apiRetrofitConexion.getClient(URL_SERVER);
    ApiCallsForLogin apiConexion = retrofit.create(ApiCallsForLogin.class);

    public void recoveryPass(String usuario, final RecoveryPasswordActivity recoveryPasswordActivity) {

        recoveryPasswordActivity.showLoading();
        // RxJava
        RecoveryPasswordObject recoveryPasswordObject = new RecoveryPasswordObject(usuario);
        apiConexion.forgetPass(recoveryPasswordObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Login process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Login process", "error " + e);
                        recoveryPasswordActivity.showContent();
                        recoveryPasswordActivity.userNotExits();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Log.d("Recovery process", "okRecovery");
                        recoveryPasswordActivity.showContent();
                        // se muestra un toast de correcto
                        recoveryPasswordActivity.passEmailSend();
                    }

                });
    }
}
