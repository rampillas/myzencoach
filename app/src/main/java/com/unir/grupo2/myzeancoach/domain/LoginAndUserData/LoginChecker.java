package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import com.unir.grupo2.myzeancoach.data.UserData.UserObject;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginFragment;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit.MetodosRetrofitLlamadaAPI;

import retrofit2.http.Field;
import rx.Observable;
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

    public boolean Login(String User, String Password,LoginFragment LoginFragment){
        ValidarUsuario(User,Password, LoginFragment);
        //llamar a servidor
        return true;
    }

    MetodosRetrofitLlamadaAPI conexionAPI = new MetodosRetrofitLlamadaAPI() {
        @Override
        public Observable<UserObject> savePost(@Field("usuario") String Usuario, @Field("contrasena") String Contrasena) {
            return null;
        }
    };

    public void ValidarUsuario(String Usuario, String Contrasena, final LoginFragment LoginFragment) {

        // RxJava
        conexionAPI.savePost(Usuario, Contrasena).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserObject userObject) {
                        if (userObject.getExiste() == 1) {
                            //llamar a la ACTIVITY que gestiona los fragments para que cambie de fragment
                        } else {
                           // LoginFragment.showIncorrectPassword();
                        }
                    }


                });
    }



}
