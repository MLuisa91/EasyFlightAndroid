package com.example.flightextrem.activity;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightextrem.R;
import com.example.flightextrem.components.ReservasAdapter;
import com.example.flightextrem.service.pojo.Reserva;
import com.example.flightextrem.service.pojo.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservasActivity extends AppCompatActivity {

    TextView usuarioLogin;
    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout home, nueva, reservas, datos, about, exit;
    List<Reserva> listReservas;
    private static Usuario usuario;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        menu = findViewById(R.id.menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        home = findViewById(R.id.home);
        nueva = findViewById(R.id.nuevaReserva);
        reservas = findViewById(R.id.reservas);
        datos = findViewById(R.id.datos);
        about = findViewById(R.id.about);
        exit = findViewById(R.id.exit);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReservasActivity.this, HomeActivity.class);
            }
        });
        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReservasActivity.this, NuevaReservaActivity.class);
            }
        });
        reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReservasActivity.this, ReservasActivity.class);
            }
        });
        datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReservasActivity.this, DatosActivity.class);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReservasActivity.this, AboutActivity.class);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ReservasActivity.this, MainActivity.class);
            }
        });

        init();
        usuario = getUsuarioContext();
        if (usuario != null)
            usuarioLogin.setText("Bienvenido, " + usuario.getNombre().concat(" ").concat(usuario.getApellidos()));
    }

    /**
     * para obtener el usuario del contexto
     *
     * @return Usuario
     */
    public Usuario getUsuarioContext() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        } else {
            return (Usuario) extras.get("USUARIO");
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
        listReservas = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            listReservas.add(new Reserva("0111258", usuario, null, null, 2, new Double(150.5), null, LocalDate.now(), null));
            listReservas.add(new Reserva("3652400", usuario, null, null, 1, new Double(80.25), null, LocalDate.now(), null));
        }
        ReservasAdapter adapter = new ReservasAdapter(listReservas, this, new ReservasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reserva item) {
                Toast.makeText(ReservasActivity.this, "Abriendo documento " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.listaReservas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}