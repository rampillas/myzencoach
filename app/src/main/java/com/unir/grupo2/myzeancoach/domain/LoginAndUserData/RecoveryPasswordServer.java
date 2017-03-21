package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.ApiCallsForLogin;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RecoveryPasswordObject;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RetrofitClient;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.RecoveryPasswordFragment;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andres on 28/02/2017.
 */

public class RecoveryPasswordServer {
    RetrofitClient apiRetrofitConexion = new RetrofitClient();
    Retrofit retrofit = apiRetrofitConexion.getClient("http://demendezr.pythonanywhere.com/");
    ApiCallsForLogin apiConexion =retrofit.create(ApiCallsForLogin.class);
    public void recoveryPass(String usuario, final RecoveryPasswordFragment recoveryPasswordFragment) {

        recoveryPasswordFragment.showLoading();
        // RxJava
        RecoveryPasswordObject recoveryPasswordObject =new RecoveryPasswordObject(usuario);
        apiConexion.forgetPass(recoveryPasswordObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecoveryPasswordObject>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Login process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Login process", "error "+e);
                        recoveryPasswordFragment.showError();
                        recoveryPasswordFragment.errorServer();
                    }

                    @Override
                    public void onNext(RecoveryPasswordObject userObject) {
                        if (true) {
                            Log.d("Recovery process", "okRecovery");
                            recoveryPasswordFragment.showContent();
                            // se muestra un toast de correcto
                            recoveryPasswordFragment.passEmailSend();
                            FragmentTransaction xfragmentTransaction = recoveryPasswordFragment.getFragmentManager().beginTransaction();
                            xfragmentTransaction.replace(R.id.container_view,new LoginFragment()).commit();
                        } else {
                            Log.d("Recovery process", "user not exits");
                            recoveryPasswordFragment.showContent();
                            recoveryPasswordFragment.userNotExits();
                        }
                    }


                });
    }
}
