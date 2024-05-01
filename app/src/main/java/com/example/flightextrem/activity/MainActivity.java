package com.example.flightextrem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flightextrem.R;
import com.example.flightextrem.service.ApiService;
import com.example.flightextrem.service.HTTPClientRetrofit;
import com.example.flightextrem.service.pojo.Usuario;
import com.example.flightextrem.utils.JsonConverter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        JsonConverter<Usuario> jsonConverter = new JsonConverter(Usuario.class);

        usuario = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);
        login = findViewById(R.id.login_button);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);

                apiService.callUsuarioByUserAndPassword(usuario.getText().toString(),password.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                Usuario usuario = jsonConverter.jsonToObject(response.body());
                                Toast.makeText(MainActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                                //intent.putExtra("usuario",usuario);
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Se ha producido un error en la aplicación", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Login incorrecto", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}