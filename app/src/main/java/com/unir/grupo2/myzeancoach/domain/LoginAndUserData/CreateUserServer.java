package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.User;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.CreateUserFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.MetodosRetrofitLlamadaAPI;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.RetrofitCliente;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.MEssentialInfoFragment;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andres on 28/02/2017.
 */

public class CreateUserServer {

    RetrofitCliente conexionAPIretrofit = new RetrofitCliente();
    Retrofit retrofit =conexionAPIretrofit.getClient("http://demendezr.pythonanywhere.com/");
    MetodosRetrofitLlamadaAPI conexioAPI=retrofit.create(MetodosRetrofitLlamadaAPI.class);
    public void NewUser(String UsuarioValor, String EmailValor, String NombreValor, String ApellidoValor,
                        String PasswordValor, String NacimientoValor, String SexoValor, String PaisValor,
                        String CiudadValor, String DescripcionValor, String ZonaValor, String CambioPaisValor,
                        String EstudiosValor, final CreateUserFragment createUserFragment) {

        createUserFragment.pageLoader.startProgress();
        createUserFragment.pageLoader.setVisibility(View.VISIBLE);
        // RxJava
        conexioAPI.createUser(UsuarioValor, EmailValor, NombreValor, ApellidoValor,PasswordValor,NacimientoValor,
                SexoValor,PaisValor,CiudadValor,DescripcionValor,ZonaValor,CambioPaisValor,EstudiosValor).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Create process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Create process", "error "+e);
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
                            xfragmentTransaction.replace(R.id.container_view,new MEssentialInfoFragment()).commit();
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
