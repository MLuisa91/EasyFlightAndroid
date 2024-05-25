package com.example.flightextrem.service;

import com.example.flightextrem.service.pojo.Extra;
import com.example.flightextrem.service.pojo.Oferta;
import com.example.flightextrem.service.pojo.Pais;
import com.example.flightextrem.service.pojo.Post;
import com.example.flightextrem.service.pojo.Reserva;
import com.example.flightextrem.service.pojo.Usuario;
import com.example.flightextrem.service.pojo.Vuelo;

import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface ApiService {

    @GET("usuarios/{user}/{password}")
    Call<String> callUsuarioByUserAndPassword(@Path("user") String user,@Path("password") String password);

    @GET("usuarios/{id}")
    Call<String> callUsuarioById(@Path("id") Integer id);

    @POST("usuarios/insertar")
    Call<Usuario> callInsertarUsuario(@Body Usuario usuario);

    @PUT("usuarios/actualizar")
    Call<Usuario> callActualizarUsuario(@Body Usuario usuario);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @POST("paises/search")
    Call<String> callPaises();

    @POST("aeropuertos/search")
    Call<String> callAeropuertos();

    @POST("reservas/search")
    Call<String> callReservas(@Body Reserva reserva);

    @GET("reservas/{id}")
    Call<String> callReservaById(@Path("id") String id);

    @GET("reservas/reservas-user/{usuario}")
    Call<String> callReservaByUsuario(@Path("usuario") Integer usuario);

    //@Streaming
    @GET("reservas/generateQr/{id}")
    Call<ResponseBody> callReservasQr(@Path("id") Integer reserva);

    @PUT("reservas/actualizar")
    Call<String> callActualizarReserva(@Body Reserva reserva);

    @POST("reservas/insertar")
    Call<String> callInsertarReserva(@Body Reserva reserva);

    @DELETE("reservas/{id}")
    Call<String> callDeleteReserva(@Path("id") String id);

    @POST("ofertas/ofertasValidas")
    Call<String> callSearchOferta(@Body Oferta oferta);

    @POST("vuelos/searchLimitado")
    Call<String> callVuelos(@Body Vuelo vuelo);

    @POST("extras/search")
    Call<String> callExtras();

    @POST("respaldos/create")
    Call<String> callBackup();

}
