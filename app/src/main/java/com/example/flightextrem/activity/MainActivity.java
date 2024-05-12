package com.example.flightextrem.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flightextrem.R;
import com.example.flightextrem.service.ApiService;
import com.example.flightextrem.service.HTTPClientRetrofit;
import com.example.flightextrem.service.pojo.Usuario;
import com.example.flightextrem.utils.JsonConverter;
import com.example.flightextrem.utils.Utiles;

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

                apiService.callUsuarioByUserAndPassword(usuario.getText().toString(), Utiles.encriptarAMD5(password.getText().toString())).enqueue(new Callback<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                Usuario usuario = jsonConverter.jsonToObject(response.body());
                                if(accesoClientes(usuario)){
                                    Toast.makeText(MainActivity.this, "Login correcto.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("USUARIO", usuario);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MainActivity.this, "El usuario no es cliente de la aplicación.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Se ha producido un error en la aplicación.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show();
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

    public void nuevoRegistro(View view) {
        Intent intent = new Intent(MainActivity.this, DatosActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean accesoClientes(Usuario usuario){
        return usuario.getUsuarioRol().stream().filter(rol -> rol.getRol().getId().equals(2)).findAny().isPresent();
    }
}