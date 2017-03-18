package com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit;


public class RegisterBody {
    String client_id;
    String client_secret;
    String username;
    String password;

    public String getUsername() {
        return username;
    }


    String grant_type;
    String scope;

    public RegisterBody(String client_id, String client_secret, String username, String password, String grant_type, String scope) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.scope = scope;
    }
}

