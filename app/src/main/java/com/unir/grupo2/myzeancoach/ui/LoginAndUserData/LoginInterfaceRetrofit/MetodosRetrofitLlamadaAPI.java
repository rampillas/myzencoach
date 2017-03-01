package com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit;

import com.unir.grupo2.myzeancoach.data.UserData.UserObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by andres on 28/02/2017.
 */


    public interface MetodosRetrofitLlamadaAPI {

        @POST("/login")
        @FormUrlEncoded
        Observable<UserObject> loginUser(@Field("usuario") String Usuario,
                                        @Field("contrasena") String Contrasena);
    @POST("/createuser")
    @FormUrlEncoded
    Observable<UserObject> createUser(@Field("usuario") String Usuario,
                                    @Field("contrasena") String Contrasena);
    }
