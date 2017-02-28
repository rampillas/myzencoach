package com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit;

import com.unir.grupo2.myzeancoach.data.UserData.UserObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by andres on 28/02/2017.
 */


    public interface MetodosRetrofitLlamadaAPI {

        @POST("/login")
        @FormUrlEncoded
        rx.Observable<UserObject> savePost(@Field("usuario") String Usuario,
                                           @Field("contrasena") String Contrasena);
    }
