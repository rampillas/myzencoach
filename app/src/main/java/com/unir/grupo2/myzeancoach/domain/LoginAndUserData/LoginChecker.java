package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.util.Log;

import com.unir.grupo2.myzeancoach.data.UserData.UserObject;
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
    public static UserObject UserObject;

    public boolean UserAndPassWordFilled(String User, String Password){
        if (User.length()==0 || Password.length()==0){
            return false;
        }else {
            return true;
        }
    }

    public void Login(String User, String Password,LoginFragment LoginFragment){
        ValidarUsuario(User,Password, LoginFragment);
        Log.d("Login process", "iniciando "+User+" "+Password);

    }

    RetrofitCliente conexionAPIretrofit = new RetrofitCliente();
    Retrofit retrofit =conexionAPIretrofit.getClient("http://proto-fep.com:16913/");
    MetodosRetrofitLlamadaAPI conexioAPI=retrofit.create(MetodosRetrofitLlamadaAPI.class);


    public void ValidarUsuario(String Usuario, String Contrasena, final LoginFragment LoginFragment) {

        // RxJava
        conexioAPI.savePost(Usuario, Contrasena).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserObject>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Login process", "completado");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Login process", "error "+e);
                    }

                    @Override
                    public void onNext(UserObject userObject) {
                        if (userObject.getExiste() == 1) {
                            Log.d("Login process", "okLogin");
                            //llamar a la ACTIVITY que gestiona los fragments para que cambie de fragment
                        } else {
                            Log.d("Login process", "incorrectpass");
                           LoginFragment.showIncorrectPassword();
                        }
                    }


                });
    }



}
