package com.example.flightextrem.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightextrem.R;
import com.example.flightextrem.components.OfertasAdapter;
import com.example.flightextrem.service.ApiService;
import com.example.flightextrem.service.HTTPClientRetrofit;
import com.example.flightextrem.service.pojo.Oferta;
import com.example.flightextrem.service.pojo.Pais;
import com.example.flightextrem.service.pojo.Usuario;
import com.example.flightextrem.service.pojo.Vuelo;
import com.example.flightextrem.utils.JsonConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    TextView usuarioLogin;
    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout home, nueva, reservas, datos, about, exit;
    private List<Oferta> ofertas;
    private static Usuario usuario;
    private HTTPClientRetrofit httpClientRetrofit;
    // Layout Manager
    RecyclerView.LayoutManager reciclerviewLayoutManager;

    @SuppressLint({"ResourceType", "WrongViewCast"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        menu = findViewById(R.id.menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        home = findViewById(R.id.home);
        nueva = findViewById(R.id.nuevaReserva);
        reservas = findViewById(R.id.reservas);
        datos = findViewById(R.id.datos);
        about = findViewById(R.id.about);
        exit = findViewById(R.id.exit);
        usuarioLogin = findViewById(R.id.txtUsuarioLogin);
        httpClientRetrofit = new HTTPClientRetrofit();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HomeActivity.this, NuevaReservaActivity.class);
            }
        });
        reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HomeActivity.this, ReservasActivity.class);
            }
        });
        datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HomeActivity.this, DatosActivity.class);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HomeActivity.this, AboutActivity.class);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HomeActivity.this, MainActivity.class);
            }
        });

        init();
        usuario = getUsuarioContext();
        if(usuario != null)
            usuarioLogin.setText("Bienvenido, " + usuario.getNombre().concat(" ").concat(usuario.getApellidos()));

    }

    /**
     * para obtener el usuario del contexto
     * @return Usuario
     */
    public Usuario getUsuarioContext() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        } else {
            return (Usuario)extras.get("USUARIO");
        }
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("USUARIO", usuario);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init() {
        JsonConverter<Oferta[]> jsonConverter = new JsonConverter(Oferta[].class);
        ofertas = new ArrayList<>();

        ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);
        apiService.callSearchOferta(new Oferta()).enqueue(new Callback<String>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(call.isExecuted() && response.body() != null){
                    ofertas = Arrays.asList(jsonConverter.jsonToObject(response.body()).clone());
                    OfertasAdapter adapter = new OfertasAdapter(ofertas, HomeActivity.this, new OfertasAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Oferta item) {

                        }
                    });
                    RecyclerView recyclerView = findViewById(R.id.listaOfertas);
                    reciclerviewLayoutManager
                            = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);
                    ;

                    // Set LayoutManager on Recycler View
                    recyclerView.setLayoutManager(
                            reciclerviewLayoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error en la carga de las ofertas.", Toast.LENGTH_LONG).show();
            }
        });


    }
}