package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.data.UserData.UserObject;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.MetodosRetrofitLlamadaAPI;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.RetrofitCliente;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.RecoveryPasswordFragment;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andres on 28/02/2017.
 */

public class RecoveryPasswordServer {
    RetrofitCliente conexionAPIretrofit = new RetrofitCliente();
    Retrofit retrofit =conexionAPIretrofit.getClient("http://proto-fep.com:16913/");
    MetodosRetrofitLlamadaAPI conexioAPI=retrofit.create(MetodosRetrofitLlamadaAPI.class);
    public void RecoveryPass(String Usuario,  final RecoveryPasswordFragment LoginFragment) {

        LoginFragment.pageLoader.startProgress();
        LoginFragment.pageLoader.setVisibility(View.VISIBLE);
        // RxJava
        conexioAPI.passOlvidada(Usuario).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserObject>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Login process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Login process", "error "+e);
                        LoginFragment.pageLoader.stopProgressAndFailed();
                        LoginFragment.pageLoader.setVisibility(View.GONE);
                        LoginFragment.errorServer();
                    }

                    @Override
                    public void onNext(UserObject userObject) {
                        if (userObject.getExiste() == 1) {
                            Log.d("Recovery process", "okRecovery");
                            LoginFragment.pageLoader.stopProgress();
                            // se muestra un toast de correcto
                            LoginFragment.passEmailSend();
                            FragmentTransaction xfragmentTransaction = LoginFragment.getFragmentManager().beginTransaction();
                            xfragmentTransaction.replace(R.id.container_view,new LoginFragment()).commit();
                        } else {
                            Log.d("Recovery process", "user not exits");
                            LoginFragment.pageLoader.stopProgress();
                            LoginFragment.userNotExits();
                            LoginFragment.pageLoader.setVisibility(View.GONE);
                        }
                    }


                });
    }
}
