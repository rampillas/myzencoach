package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.data.UserData.UserObject;
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
    public void Login(String User, String Password,CreateUserFragment LoginFragment){
        ValidarUsuario(User,Password, LoginFragment);
        Log.d("Login process", "iniciando "+User+" "+Password);

    }

    RetrofitCliente conexionAPIretrofit = new RetrofitCliente();
    Retrofit retrofit =conexionAPIretrofit.getClient("http://proto-fep.com:16913/");
    MetodosRetrofitLlamadaAPI conexioAPI=retrofit.create(MetodosRetrofitLlamadaAPI.class);


    public void ValidarUsuario(String Usuario, String Contrasena, final LoginFragment LoginFragment) {

        LoginFragment.pageLoader.startProgress();
        LoginFragment.pageLoader.setVisibility(View.VISIBLE);
        // RxJava
        conexioAPI.loginUser(Usuario, Contrasena).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                            Log.d("Login process", "okLogin");
                            LoginFragment.pageLoader.stopProgress();

                            //se guarda el usuario y clave para mantener la sesion
                            Context context = LoginFragment.getActivity();
                            SharedPreferences sharedPref = context.getSharedPreferences(
                                    LoginFragment.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(LoginFragment.getString(R.string.PREFERENCES_USER),userObject.getUsuario());
                            editor.putString(LoginFragment.getString(R.string.PREFERENCES_PASSWORD),userObject.getContrasena());
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
    public void ValidarUsuario(String Usuario, String Contrasena, final CreateUserFragment LoginFragment) {

        LoginFragment.pageLoader.startProgress();
        LoginFragment.pageLoader.setVisibility(View.VISIBLE);
        // RxJava
        conexioAPI.loginUser(Usuario, Contrasena).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
