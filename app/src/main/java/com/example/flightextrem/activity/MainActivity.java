package com.example.flightextrem.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.flightextrem.R;
import com.example.flightextrem.service.ApiService;
import com.example.flightextrem.service.HTTPClientRetrofit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private HTTPClientRetrofit httpClientRetrofit;
    EditText usuario;
    EditText password;
    Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpClientRetrofit = new HTTPClientRetrofit();

        usuario = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);
        login = findViewById(R.id.login_button);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario.getText().toString().equals("Admin") && password.getText().toString().equals("1234")) {
                    Toast.makeText(MainActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Login incorrecto", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);

        apiService.callHolaMundo().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String post = response.body();
// Handle the post data
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getCause());
            }
        });*/
    }


}