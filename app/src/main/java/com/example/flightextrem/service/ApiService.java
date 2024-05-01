package com.example.flightextrem.service;

import com.example.flightextrem.service.pojo.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("usuarios/{user}/{password}")
    Call<String> callUsuarioByUserAndPassword(@Path("user") String user,@Path("password") String password);

    @POST("posts")
    Call<Post> createPost(@Body Post post);
}
