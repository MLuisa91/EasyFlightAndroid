package com.example.flightextrem.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.flightextrem.R;
import com.example.flightextrem.components.VuelosAdapter;
import com.example.flightextrem.components.ListVuelos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NuevaReservaActivity extends AppCompatActivity {

    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout home, nueva, reservas, datos, about, exit;
    List<ListVuelos> vuelos;
    DatePicker datePickerSalida, datePickerVuelta;
    TextView fechaSalida, fechaVuelta;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_reserva);
        menu = findViewById(R.id.menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        home = findViewById(R.id.home);
        nueva = findViewById(R.id.nuevaReserva);
        reservas = findViewById(R.id.reservas);
        datos = findViewById(R.id.datos);
        about = findViewById(R.id.about);
        exit = findViewById(R.id.exit);
        fechaSalida = findViewById(R.id.txtSearchFechaSalida);
        fechaVuelta = findViewById(R.id.txtSearchFechaVuelta);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NuevaReservaActivity.this, NavigationActivity.class);
            }
        });
        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NuevaReservaActivity.this, ReservasActivity.class);
            }
        });
        datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NuevaReservaActivity.this, DatosActivity.class);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NuevaReservaActivity.this, AboutActivity.class);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NuevaReservaActivity.this, MainActivity.class);
            }
        });

        init();
        initDatePickers();
        initSpinners();
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
        vuelos = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vuelos.add(new ListVuelos("Madrid", "Ibiza", LocalDate.now(), new Double("120")));
            vuelos.add(new ListVuelos("Sevilla", "Barcelona", LocalDate.now(), new Double("200")));
        }
        VuelosAdapter adapter = new VuelosAdapter(vuelos, this);
        RecyclerView recyclerView = findViewById(R.id.listaVuelos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Para cargar los spinners de la aplicación
     */
    public void initSpinners() {
        List<String> origenDestino = new ArrayList<>();
        origenDestino.add("Seleccione una opción");
        origenDestino.add("Madrid");
        origenDestino.add("Badajoz");
        origenDestino.add("Sevilla");
        Spinner spinnerOrigen = (Spinner) findViewById(R.id.searchOrigen);
        Spinner spinnerDestino = (Spinner) findViewById(R.id.searchDestino);
        // Crear el adaptador
        ArrayAdapter adapterOrigen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, origenDestino);
        ArrayAdapter adapterDestino = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, origenDestino);

        adapterOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOrigen.setAdapter(adapterOrigen);
        spinnerDestino.setAdapter(adapterDestino);
    }

    /**
     * Para cargar los datepickers de la aplicación
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initDatePickers() {
        fechaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtenemos la instancia de calendar
                final Calendar c = Calendar.getInstance();

                // obtenemos el día el mes y el año actual, para cuando abramos el datepicker se lo podamos establecer
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NuevaReservaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                fechaSalida.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        // el año mes y día actual se lo pasamos al datepiker
                        year, month, day);
                // mostramos el datepicker
                datePickerDialog.show();
            }
        });
        fechaVuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtenemos la instancia de calendar
                final Calendar c = Calendar.getInstance();

                int anio = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);

                // obtenemos el día el mes y el año actual, para cuando abramos el datepicker se lo podamos establecer
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        NuevaReservaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // establecemos la fecha seleccionada en el datepiker en el campo de texto
                                fechaVuelta.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        // el año mes y día actual se lo pasamos al datepiker
                        anio, mes, dia);
                // y mostramos el datepicker
                datePickerDialog.show();
            }
        });
    }
}