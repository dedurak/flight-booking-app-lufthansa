package com.example.lufthansa.APIObjects;
import retrofit2.Retrofit;

public class AccessTokenObject {
    private String access_token;
    private String token_type;
    private long expires_in;

    public String getAccess_token() { return access_token; }
}
