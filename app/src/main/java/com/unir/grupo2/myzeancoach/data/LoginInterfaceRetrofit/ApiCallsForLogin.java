package com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit;

import com.unir.grupo2.myzeancoach.domain.model.Token;
import com.unir.grupo2.myzeancoach.domain.model.User;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by andres on 28/02/2017.
 */


public interface ApiCallsForLogin {

    @Headers("Content-Type: application/json")
    @POST("login/")
    // se ha creado un objeto register body para que la informacion del cuerpo pueda viajar ademas de quitar la etiqueta @FormUrlEncoded
    Observable<Token> loginUser(@Body RegisterBody rb);
    //recuperar usuario
    @Headers("Content-Type: application/json")
    @GET("users/{username}")
    // se ha creado un objeto register body para que la informacion del cuerpo pueda viajar ademas de quitar la etiqueta @FormUrlEncoded
    Observable<User> userData(@Path("username") String user, @Header("Authorization") String access_token);
    @Headers("Content-Type: application/json")
    @POST("/password-recovery/")
    Observable<Void> forgetPass(@Body RecoveryPasswordObject rpo);

    @FormUrlEncoded
    @POST("/users/")
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
                                @Field("level_studies") String Estudios,
                                @Field("notification_token") String notificationToken);


}
