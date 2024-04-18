package com.example.flightextrem.service;

import com.example.flightextrem.service.response.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int postId);

    @GET("hola/mundo")
    Call<String> callHolaMundo();

    @POST("posts")
    Call<Post> createPost(@Body Post post);
}
