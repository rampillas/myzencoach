package com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginInterfaceRetrofit;

/**
 * Created by andres on 28/02/2017.
 */

public class CreadorPeticionRetrofit {

    private CreadorPeticionRetrofit() {}

    public static final String BASE_URL = "http://proto-fep.com:16913/";

    public static MetodosRetrofitLlamadaAPI getAPIService() {

        return RetrofitCliente.getClient(BASE_URL).create(MetodosRetrofitLlamadaAPI.class);
    }
}