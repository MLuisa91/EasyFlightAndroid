package com.example.flightextrem.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        JsonSerializer<LocalDate> serializeLocalDate = new JsonSerializer<LocalDate>() {
            @Override
            public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.toString());
            }
        };
        JsonSerializer<LocalTime> serializeLocalTime = new JsonSerializer<LocalTime>() {
            @Override
            public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.toString());
            }
        };
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, serializeLocalDate)
                .registerTypeAdapter(LocalTime.class, serializeLocalTime)
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
