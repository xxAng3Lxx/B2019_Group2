package com.b2019.studentspadmysql.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseURL = "https://cheek-waves.000webhostapp.com/tbl_Notes/";
    private static Retrofit retro;

    public static Retrofit konekRetrofit(){
        if(retro == null) {
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retro;
    }
}
