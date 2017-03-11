package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.ApiCallsForLogin;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RetrofitClient;
import com.unir.grupo2.myzeancoach.domain.model.User;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.CreateUserFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginFragment;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by andres on 28/02/2017.
 */

public class LoginChecker {
    public static User userObject;

    public boolean UserAndPassWordFilled(String user, String password) {
        if (user.length() == 0 || password.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void Login(String idFijo, String secretFijo, String user, String password, String tipoFijo, String scopeFijo, LoginFragment loginFragment) {
        validateUser(idFijo, secretFijo, user, password, tipoFijo, scopeFijo, loginFragment);
        Log.d("Login process", "iniciando " + user + " " + password);

    }

    public void Login(String idFijo, String secretFijo, String user, String Password, String tipoFijo, String scopeFijo, CreateUserFragment createUserFragment) {
        validateUser(idFijo, secretFijo, user, Password, tipoFijo, scopeFijo, createUserFragment);
        Log.d("Login process", "iniciando " + user + " " + Password);

    }

    RetrofitClient conexionAPIretrofit = new RetrofitClient();
    Retrofit retrofit = conexionAPIretrofit.getClient("http://demendezr.pythonanywhere.com/");
    ApiCallsForLogin apiConexion = retrofit.create(ApiCallsForLogin.class);


    public void validateUser(String idFijo, String secretFijo, String usuario, String contrasena, String tipoFijo, String scopeFijo, final LoginFragment loginFragment) {

        loginFragment.showLoading();
        // RxJava
        apiConexion.loginUser(idFijo, secretFijo, usuario, contrasena, tipoFijo, scopeFijo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Login process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Login process", "error " + e);
                        loginFragment.showError();
                        loginFragment.errorServer();
                    }

                    @Override
                    public void onNext(User userObject) {
                        if (userObject.getIsActive()) {
                            Log.d("Login process", "okLogin");
                            loginFragment.showContent();

                            //se guarda el usuario y clave para mantener la sesion
                            Context context = loginFragment.getActivity();
                            SharedPreferences sharedPref = context.getSharedPreferences(
                                    loginFragment.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(loginFragment.getString(R.string.PREFERENCES_USER), userObject.getUsername());
                            editor.putString(loginFragment.getString(R.string.PREFERENCES_PASSWORD), userObject.getPassword());
                            editor.commit();

                            //se cambia la vista
                            loginFragment.showContent();
                            FragmentTransaction xfragmentTransaction = loginFragment.getFragmentManager().beginTransaction();
                            xfragmentTransaction.replace(R.id.container_view, new LoginFragment()).commit();
                        } else {
                            Log.d("Login process", "incorrectpass");
                            loginFragment.showError();
                            loginFragment.showIncorrectPassword();
                            loginFragment.showContent();
                        }
                    }


                });
    }


    //recreamos la clase para ser llamada desde la pantalla de Crear Usuario
    public void validateUser(String idFijo, String secretFijo, String usuarioo, String contrasena, String tipoFijo, String scopeFijo, final CreateUserFragment createUserFragment) {

        createUserFragment.showLoading();
        // RxJava
        apiConexion.loginUser(idFijo, secretFijo, usuarioo, contrasena, tipoFijo, scopeFijo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Login process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Login process", "error " + e);
                        createUserFragment.showError();
                        createUserFragment.errorServer();
                    }

                    @Override
                    public void onNext(User userObject) {
                        if (userObject.getIsActive()) {
                            Log.d("Login process", "okLogin");
                            createUserFragment.showContent();
                            createUserFragment.showFieldsIntoCases(userObject);

                        } else {
                            Log.d("Login process", "incorrectpass");
                            createUserFragment.showContent();
                            createUserFragment.userExits();
                        }
                    }


                });
    }


}
