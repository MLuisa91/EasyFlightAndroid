package com.example.flightextrem.service;

import com.example.flightextrem.service.pojo.Extra;
import com.example.flightextrem.service.pojo.Oferta;
import com.example.flightextrem.service.pojo.Pais;
import com.example.flightextrem.service.pojo.Post;
import com.example.flightextrem.service.pojo.Reserva;
import com.example.flightextrem.service.pojo.Usuario;
import com.example.flightextrem.service.pojo.Vuelo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @PUT("reservas/actualizar")
    Call<String> callActualizarReserva(@Body Reserva reserva);

    @POST("reservas/insertar")
    Call<String> callInsertarReserva(@Body Reserva reserva);

    @DELETE("reservas/{id}")
    Call<String> callDeleteReserva(@Path("id") String id);

    @POST("ofertas/search")
    Call<String> callSearchOferta(@Body Oferta oferta);

    @POST("vuelos/searchLimitado")
    Call<String> callVuelos(@Body Vuelo vuelo);

    @POST("extras/search")
    Call<String> callExtras();

}
