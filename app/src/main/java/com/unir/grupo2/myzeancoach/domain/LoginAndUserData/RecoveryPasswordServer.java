package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.User;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.ApiCallsForLogin;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.RetrofitClient;
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
    Retrofit retrofit = apiRetrofitConexion.getClient("http://proto-fep.com:16913/");
    ApiCallsForLogin apiConexion =retrofit.create(ApiCallsForLogin.class);
    public void recoveryPass(String usuario, final RecoveryPasswordFragment recoveryPasswordFragment) {

        recoveryPasswordFragment.pageLoader.startProgress();
        recoveryPasswordFragment.pageLoader.setVisibility(View.VISIBLE);
        // RxJava
        apiConexion.forgetPass(usuario).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Login process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Login process", "error "+e);
                        recoveryPasswordFragment.pageLoader.stopProgressAndFailed();
                        recoveryPasswordFragment.pageLoader.setVisibility(View.GONE);
                        recoveryPasswordFragment.errorServer();
                    }

                    @Override
                    public void onNext(User userObject) {
                        if (userObject.getIsActive()) {
                            Log.d("Recovery process", "okRecovery");
                            recoveryPasswordFragment.pageLoader.stopProgress();
                            // se muestra un toast de correcto
                            recoveryPasswordFragment.passEmailSend();
                            FragmentTransaction xfragmentTransaction = recoveryPasswordFragment.getFragmentManager().beginTransaction();
                            xfragmentTransaction.replace(R.id.container_view,new LoginFragment()).commit();
                        } else {
                            Log.d("Recovery process", "user not exits");
                            recoveryPasswordFragment.pageLoader.stopProgress();
                            recoveryPasswordFragment.userNotExits();
                            recoveryPasswordFragment.pageLoader.setVisibility(View.GONE);
                        }
                    }


                });
    }
}
