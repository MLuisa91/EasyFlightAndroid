package com.example.flightextrem.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HTTPClientRetrofit {

    private Retrofit httpConection;

    private static final String PROTOCOLO = "http";
    private static final String DOMINIO = "192.168.56.1";
    private static final String PUERTO="9999";
    private static final String URLAPI = "easyflight";

    public HTTPClientRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        this.httpConection = new Retrofit.Builder()
                .baseUrl(PROTOCOLO.concat("://").concat(DOMINIO).concat(":").concat(PUERTO).concat("/").concat(URLAPI).concat("/"))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getHttpRetrofitConection(){
        return this.httpConection;
    }

}
