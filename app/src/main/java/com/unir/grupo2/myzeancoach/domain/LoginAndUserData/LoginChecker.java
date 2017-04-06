package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.ApiCallsForLogin;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RegisterBody;
import com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit.RetrofitClient;
import com.unir.grupo2.myzeancoach.domain.model.Token;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginActivity;

import me.pushy.sdk.Pushy;
import me.pushy.sdk.util.exceptions.PushyException;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andres on 28/02/2017.
 */

public class LoginChecker {

    public boolean UserAndPassWordFilled(String user, String password) {
        if (user.length() == 0 || password.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void Login(String contentType, String user, String pass, LoginActivity loginActivity) {
        validateUser(user, pass, loginActivity);
    }

    RetrofitClient conexionAPIretrofit = new RetrofitClient();
    Retrofit retrofit = conexionAPIretrofit.getClient("http://demendezr.pythonanywhere.com/");
    ApiCallsForLogin apiConexion = retrofit.create(ApiCallsForLogin.class);


    public void validateUser(String user, String pass, final LoginActivity loginActivity) {

        loginActivity.showLoading();
        // RxJava
        final RegisterBody rb = new RegisterBody("clientweb2231", "secretweb2231", user, pass, "password", "read+write");
        apiConexion.loginUser(rb).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Login process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Login process", "error " + e);
                        String asd = e.getMessage();
                        if (e.getMessage().equals("HTTP 401 UNAUTHORIZED")) {
                            showWrongPassword(loginActivity);
                        }
                    }

                    @Override
                    public void onNext(Token token) {
                        if (token.getAccessToken() != null) {
                            Log.d("Login process token= ", token.getAccessToken());

                            //se guarda el token usuario y clave para mantener la sesion
                            Utils.saveUserInPreference(loginActivity.getBaseContext(), token.getAccessToken(), rb.getUsername());
                            loginActivity.launchMainActivity();
                            //se cambia la vista
                            loginActivity.showContent();
                            //crashlytics
                            // You can call any combination of these three methods
                            // Assign a unique token to this device
                            try {
                                String deviceToken = Pushy.register(loginActivity.getApplicationContext());
                                Crashlytics.setUserIdentifier(deviceToken);
                                Crashlytics.setUserName(rb.getUsername());
                            } catch (PushyException e) {
                                Crashlytics.setUserIdentifier("error on get context");
                                Crashlytics.setUserName(rb.getUsername());
                                e.printStackTrace();
                            }



                        } else {
                            showWrongPassword(loginActivity);
                        }
                    }

                });
    }

    private void showWrongPassword(LoginActivity loginActivity) {
        Log.d("Login process", "incorrectpass");
        loginActivity.showError();
        loginActivity.showIncorrectPassword();
        loginActivity.showContent();
    }

}
