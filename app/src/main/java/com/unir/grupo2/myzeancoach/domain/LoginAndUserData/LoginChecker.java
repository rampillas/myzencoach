package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.User;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.CreateUserFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.MetodosRetrofitLlamadaAPI;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.RetrofitCliente;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by andres on 28/02/2017.
 */

public class LoginChecker {
    public static User UserObject;

    public boolean UserAndPassWordFilled(String User, String Password){
        if (User.length()==0 || Password.length()==0){
            return false;
        }else {
            return true;
        }
    }

    public void Login(String IdFijo, String SecretFijo, String User, String Password,String TipoFijo, String ScopeFijo,LoginFragment LoginFragment){
        ValidarUsuario(IdFijo,SecretFijo,User,Password, TipoFijo, ScopeFijo, LoginFragment);
        Log.d("Login process", "iniciando "+User+" "+Password);

    }
    public void Login(String IdFijo, String SecretFijo, String User, String Password,String TipoFijo, String ScopeFijo,CreateUserFragment LoginFragment){
        ValidarUsuario(IdFijo,SecretFijo,User,Password, TipoFijo, ScopeFijo,LoginFragment);
        Log.d("Login process", "iniciando "+User+" "+Password);

    }

    RetrofitCliente conexionAPIretrofit = new RetrofitCliente();
    Retrofit retrofit =conexionAPIretrofit.getClient("http://demendezr.pythonanywhere.com/");
    MetodosRetrofitLlamadaAPI conexioAPI=retrofit.create(MetodosRetrofitLlamadaAPI.class);


    public void ValidarUsuario(String IdFijo, String SecretFijo,String Usuario, String Contrasena,String TipoFijo, String ScopeFijo, final LoginFragment LoginFragment) {

        LoginFragment.pageLoader.startProgress();
        LoginFragment.pageLoader.setVisibility(View.VISIBLE);
        // RxJava
        conexioAPI.loginUser(IdFijo,SecretFijo,Usuario, Contrasena, TipoFijo, ScopeFijo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
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
                    public void onNext(User userObject) {
                        if (userObject.getIsActive()) {
                            Log.d("Login process", "okLogin");
                            LoginFragment.pageLoader.stopProgress();

                            //se guarda el usuario y clave para mantener la sesion
                            Context context = LoginFragment.getActivity();
                            SharedPreferences sharedPref = context.getSharedPreferences(
                                    LoginFragment.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(LoginFragment.getString(R.string.PREFERENCES_USER),userObject.getUsername());
                            editor.putString(LoginFragment.getString(R.string.PREFERENCES_PASSWORD),userObject.getPassword());
                            editor.commit();

                            //se cambia la vista
                            LoginFragment.pageLoader.setVisibility(View.GONE);
                            FragmentTransaction xfragmentTransaction = LoginFragment.getFragmentManager().beginTransaction();
                            xfragmentTransaction.replace(R.id.container_view,new LoginFragment()).commit();
                        } else {
                            Log.d("Login process", "incorrectpass");
                            LoginFragment.pageLoader.stopProgress();
                            LoginFragment.showIncorrectPassword();
                            LoginFragment.pageLoader.setVisibility(View.GONE);
                        }
                    }


                });
    }


    //recreamos la clase para ser llamada desde la pantalla de Crear Usuario
    public void ValidarUsuario(String IdFijo, String SecretFijo,String Usuario, String Contrasena, String TipoFijo, String ScopeFijo,final CreateUserFragment LoginFragment) {

        LoginFragment.pageLoader.startProgress();
        LoginFragment.pageLoader.setVisibility(View.VISIBLE);
        // RxJava
        conexioAPI.loginUser(IdFijo, SecretFijo,Usuario, Contrasena,TipoFijo, ScopeFijo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
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
                    public void onNext(User userObject) {
                        if (userObject.getIsActive()) {
                            Log.d("Login process", "okLogin");
                            LoginFragment.pageLoader.stopProgress();
                            LoginFragment.ponerNombresEnCasilleros(userObject);
                            //se cambia la vista
                            LoginFragment.pageLoader.setVisibility(View.GONE);

                        } else {
                            Log.d("Login process", "incorrectpass");
                            LoginFragment.pageLoader.stopProgress();
                            LoginFragment.pageLoader.setVisibility(View.GONE);
                        }
                    }


                });
    }



}
