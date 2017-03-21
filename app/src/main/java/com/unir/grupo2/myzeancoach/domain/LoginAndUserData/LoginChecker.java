package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.ApiCallsForLogin;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RegisterBody;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RetrofitClient;
import com.unir.grupo2.myzeancoach.domain.model.Token;
import com.unir.grupo2.myzeancoach.domain.model.User;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.CreateUserFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.RemaindersFragment;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by andres on 28/02/2017.
 */

public class LoginChecker{
    public static User userObject;

    public boolean UserAndPassWordFilled(String user, String password) {
        if (user.length() == 0 || password.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void Login(String contentType, String user, String pass, LoginFragment loginFragment) {
        validateUser(contentType, user, pass,loginFragment);
    }

    public void Login(String contentType,String user, String token, CreateUserFragment createUserFragment) {
        validateUser(contentType, user,token, createUserFragment);
    }

    RetrofitClient conexionAPIretrofit = new RetrofitClient();
    Retrofit retrofit = conexionAPIretrofit.getClient("http://demendezr.pythonanywhere.com/");
    ApiCallsForLogin apiConexion = retrofit.create(ApiCallsForLogin.class);


    public void validateUser(String contentType, String user, String pass, final LoginFragment loginFragment) {

        loginFragment.showLoading();
        // RxJava
        final RegisterBody rb =new RegisterBody("clientweb2231","secretweb2231", user, pass, "password","read+write");
        apiConexion.loginUser(rb).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token>() {
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
                    public void onNext(Token token) {
                        if (token.getAccessToken()!=null) {
                            Log.d("Login process token= ", token.getAccessToken());
                            loginFragment.showContent();

                            //se guarda el token usuario y clave para mantener la sesion
                            Context context = loginFragment.getActivity();
                            SharedPreferences sharedPref = context.getSharedPreferences(
                                    loginFragment.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(loginFragment.getString(R.string.PREFERENCES_TOKEN), token.getAccessToken());
                            editor.putString(loginFragment.getString(R.string.PREFERENCES_USER), rb.getUsername());
                            editor.commit();

                            //se cambia la vista
                            loginFragment.showContent();
                            FragmentTransaction xfragmentTransaction = loginFragment.getFragmentManager().beginTransaction();
                            xfragmentTransaction.replace(R.id.container_view, new RemaindersFragment()).commit();
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
    public void validateUser(String contentType, String user,String token, final CreateUserFragment createUserFragment) {

        createUserFragment.showLoading();
        // RxJava
        apiConexion.userData(user,"Beader "+token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                    public void onNext(User user) {
                        Log.d("Login process", "okLogin");
                        createUserFragment.showFieldsIntoCases(user);
                        createUserFragment.showContent();
                    }


                });
    }



}
