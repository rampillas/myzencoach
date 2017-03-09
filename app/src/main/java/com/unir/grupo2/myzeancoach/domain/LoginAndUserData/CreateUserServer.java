package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.User;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.CreateUserFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.ApiCallsForLogin;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.RetrofitClient;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.MEssentialInfoFragment;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andres on 28/02/2017.
 */

public class CreateUserServer {

    RetrofitClient conexionAPIretrofit = new RetrofitClient();
    Retrofit retrofit = conexionAPIretrofit.getClient("http://demendezr.pythonanywhere.com/");
    ApiCallsForLogin conexioAPI = retrofit.create(ApiCallsForLogin.class);

    public void newUser(String usuarioValor, String emailValor, String nombreValor, String apellidoValor,
                        String passwordValor, String nacimientoValor, String sexoValor, String paisValor,
                        String ciudadValor, String descripcionValor, String zonaValor, String cambioPaisValor,
                        String estudiosValor, final CreateUserFragment createUserFragment) {

        createUserFragment.pageLoader.startProgress();
        createUserFragment.pageLoader.setVisibility(View.VISIBLE);
        // RxJava
        conexioAPI.createUser(usuarioValor, emailValor, nombreValor, apellidoValor, passwordValor, nacimientoValor,
                sexoValor, paisValor, ciudadValor, descripcionValor, zonaValor, cambioPaisValor, estudiosValor).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Create process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Create process", "error " + e);
                        createUserFragment.pageLoader.stopProgressAndFailed();
                        createUserFragment.pageLoader.setVisibility(View.GONE);
                        createUserFragment.errorServer();
                    }

                    @Override
                    public void onNext(User userObject) {
                        if (userObject.getIsActive()) {
                            Log.d("Create process", "ok creado");
                            createUserFragment.pageLoader.stopProgress();
                            createUserFragment.pageLoader.setVisibility(View.GONE);
                            FragmentTransaction xfragmentTransaction = createUserFragment.getFragmentManager().beginTransaction();
                            xfragmentTransaction.replace(R.id.container_view, new MEssentialInfoFragment()).commit();
                        } else {
                            Log.d("Create process", "incorrect usser exits");
                            createUserFragment.pageLoader.stopProgress();
                            createUserFragment.userExits();
                            createUserFragment.pageLoader.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
