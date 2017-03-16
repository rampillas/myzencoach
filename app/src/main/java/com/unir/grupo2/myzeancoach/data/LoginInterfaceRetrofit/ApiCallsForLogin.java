package com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit;

import com.unir.grupo2.myzeancoach.domain.model.Token;
import com.unir.grupo2.myzeancoach.domain.model.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by andres on 28/02/2017.
 */


public interface ApiCallsForLogin {

    @Headers("Content-Type: application/json")
    @POST("login/")
    @FormUrlEncoded
    Observable<Token> loginUser(@Field("client_id") String IdFijo,
                                @Field("client_secret") String SecretFijo,
                                @Field("username") String Usuario,
                                @Field("password") String Contrasena,
                                @Field("grant_type") String TipoFijo,
                                @Field("scope") String ScopeFijo);
   //Observable<Token> loginUser(@Body RequestBody body);

    @POST("/password-recovery/")
    @FormUrlEncoded
    Observable<User> forgetPass(@Field("username") String usuario);

    @POST("/users/")
    @FormUrlEncoded
    Observable<User> createUser(@Field("username") String Usuario,
                                @Field("email") String Email,
                                @Field("first_name") String Nombre,
                                @Field("last_name") String Apellido,
                                @Field("password") String Contrasena,
                                @Field("birthday") String Nacimiento,
                                @Field("gender") String Sexo,
                                @Field("country") String Pais,
                                @Field("city") String Ciudad,
                                @Field("description") String Descripcion,
                                @Field("rural_zone") String Zona,
                                @Field("change_country") String CambioPais,
                                @Field("level_studies") String Estudios
    );


                       /*@Field("client_id") String IdFijo,
                               @Field("client_secret") String SecretFijo,
                               @Field("username") String Usuario,
                               @Field("password") String Contrasena,
                               @Field("grant_type") String TipoFijo,
                               @Field("scope") String ScopeFijo);*/
}
