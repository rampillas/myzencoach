package com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit;

import com.unir.grupo2.myzeancoach.domain.model.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by andres on 28/02/2017.
 */


public interface ApiCallsForLogin {

    @FormUrlEncoded
    @POST("/oauth/token")
    Observable<User> loginUser(@Field("client_id") String IdFijo,
                               @Field("client_secret") String SecretFijo,
                               @Field("username") String Usuario,
                               @Field("password") String Contrasena,
                               @Field("grant_type") String TipoFijo,
                               @Field("scope") String ScopeFijo);
            /*(@Field("usuario") String Usuario,
                               @Field("contrasena") String Contrasena);*/
    @POST("/password-recovery/")
    @FormUrlEncoded
    Observable<User> forgetPass(@Header("Content-Type") String contentType,
                                @Field("username") String usuario);

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
}
