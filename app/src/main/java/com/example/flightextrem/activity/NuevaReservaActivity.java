package com.example.flightextrem.activity;

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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightextrem.R;
import com.example.flightextrem.components.VuelosAdapter;
import com.example.flightextrem.service.ApiService;
import com.example.flightextrem.service.HTTPClientRetrofit;
import com.example.flightextrem.service.pojo.Aeropuerto;
import com.example.flightextrem.service.pojo.Oferta;
import com.example.flightextrem.service.pojo.Reserva;
import com.example.flightextrem.service.pojo.Usuario;
import com.example.flightextrem.service.pojo.Vuelo;
import com.example.flightextrem.utils.JsonConverter;
import com.example.flightextrem.utils.Utiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaReservaActivity extends AppCompatActivity {

    private static Usuario usuario;
    private static Oferta oferta;
    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout home, nueva, reservas, datos, about, exit;
    List<Vuelo> vuelos;
    DatePicker datePickerSalida, datePickerVuelta;
    TextView fechaSalida, fechaVuelta;
    TextView usuarioLogin;
    Spinner spinnerDestino, spinnerOrigen;
    Map<String, Vuelo> vuelosMap = new HashMap<>();
    private HTTPClientRetrofit httpClientRetrofit;

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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("USUARIO", usuario);
        activity.startActivity(intent);
        activity.finish();
    }

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
        usuarioLogin = findViewById(R.id.txtUsuarioLogin);
        httpClientRetrofit = new HTTPClientRetrofit();
        spinnerOrigen = findViewById(R.id.searchOrigen);
        spinnerDestino = findViewById(R.id.searchDestino);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(NuevaReservaActivity.this, HomeActivity.class);
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

        initDatePickers();
        initSpinners();
        usuario = getUsuarioContext();
        oferta = getOfertaContext();
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

    public Oferta getOfertaContext() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        } else {
            return (Oferta) extras.get("OFERTA");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void search(Vuelo vuelo, Boolean isOrigen) {
        JsonConverter<Vuelo[]> jsonConverter = new JsonConverter(Vuelo[].class);
        vuelos = new ArrayList<>();
        ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);
        apiService.callVuelos(vuelo).enqueue(new Callback<String>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call.isExecuted() && response.body() != null) {
                    vuelos = Arrays.asList(jsonConverter.jsonToObject(response.body()).clone());
                    VuelosAdapter adapter = new VuelosAdapter(vuelos, isOrigen, NuevaReservaActivity.this, new VuelosAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Vuelo item, Boolean esOrigen) {
                            goToDetail(item, esOrigen);
                        }
                    });

                    RecyclerView recyclerView = findViewById(R.id.listaVuelos);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(NuevaReservaActivity.this));
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(NuevaReservaActivity.this, "Error en la búsqueda de vuelos.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(NuevaReservaActivity.this, "Error en la búsqueda de vuelos.", Toast.LENGTH_LONG).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void goToDetail(Vuelo item, Boolean esOrigen) {
        if (!esOrigen) {
            vuelosMap.put("vuelta", item);
        } else {
            vuelosMap.put("origen", item);
        }

    }

    /**
     * Para cargar los spinners de la aplicación
     */
    public void initSpinners() {
        JsonConverter<Aeropuerto[]> jsonConverter = new JsonConverter(Aeropuerto[].class);
        List<Aeropuerto> origenDestino = new ArrayList<>();
        origenDestino.add(new Aeropuerto(0, "Seleccione una opción"));
        ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);
        apiService.callAeropuertos().enqueue(new Callback<String>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call.isExecuted() && response.body() != null) {
                    origenDestino.addAll(Arrays.asList(jsonConverter.jsonToObject(response.body()).clone()));

                    // Crear el adaptador
                    ArrayAdapter adapterOrigen = new ArrayAdapter<>(NuevaReservaActivity.this, android.R.layout.simple_spinner_item, origenDestino);
                    ArrayAdapter adapterDestino = new ArrayAdapter<>(NuevaReservaActivity.this, android.R.layout.simple_spinner_item, origenDestino);

                    adapterOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adapterDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinnerOrigen.setAdapter(adapterOrigen);
                    spinnerDestino.setAdapter(adapterDestino);
                } else {
                    Toast.makeText(NuevaReservaActivity.this, "Error en la carga de los aeropuertos.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(NuevaReservaActivity.this, "Error en la carga de los aeropuertos.", Toast.LENGTH_LONG).show();

            }
        });


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

    /**
     * Método para buscar los vuelos según los filtros seleccionados
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void buscarVuelo(View view) {
        String errores = "";
        Boolean correcto = true;
        LocalDate salidaDate = null;
        LocalDate vueltaDate = null;
        Aeropuerto destino = (Aeropuerto) spinnerDestino.getSelectedItem();
        if (destino.getId() == 0) {
            errores += "Seleccione un destino \n";
            correcto = false;
        }
        Aeropuerto origen = (Aeropuerto) spinnerOrigen.getSelectedItem();
        if (origen.getId() == 0) {
            errores += "Seleccione un origen \n";
            correcto = false;
        }
        String vuelta = String.valueOf(fechaVuelta.getText());
        if (!vuelta.isEmpty()) {
            vueltaDate = Utiles.convertirADate(vuelta);
        }
        String salida = String.valueOf(fechaSalida.getText());
        if (salida.isEmpty()) {
            errores += "Seleccione una fecha de salida para el vuelo \n";
            correcto = false;
        } else {
            salidaDate = Utiles.convertirADate(salida);
        }

        if (correcto) {
            Vuelo vueloSalida = new Vuelo(null, origen, destino, salidaDate, null, null, null, null);
            this.search(vueloSalida, true);
            if (vueltaDate != null) {
                Vuelo vueloVuelta = new Vuelo(null, destino, origen, vueltaDate, null, null, null, null);
                this.search(vueloVuelta, false);
            }
        } else {
            Toast.makeText(NuevaReservaActivity.this, errores, Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Método que nos redirige a la ventana de reserva
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void reservar(View view) {

        Intent detailIntent = new Intent(this, DetailActivity.class);
        Boolean redirecciona = true;
        Optional<Vuelo> origen = vuelosMap.entrySet().stream().filter(key -> key.getKey().equals("origen"))
                .map(valor -> valor.getValue())
                .findFirst();
        Optional<Vuelo> destino = Optional.empty();
        if(oferta!=null){

        }
        if (!String.valueOf(fechaVuelta.getText()).equals("")) {
            Long total = vuelosMap.keySet().stream().distinct().count();
            if (total == 2) {
                destino = vuelosMap.entrySet().stream().filter(key -> key.getKey().equals("destino"))
                        .map(valor -> valor.getValue())
                        .findFirst();
                detailIntent.putExtra("VUELO_DESTINO", destino.get());
            } else {
                redirecciona = false;
                Toast.makeText(NuevaReservaActivity.this, "Debe de seleccionar un origen y un destino.", Toast.LENGTH_LONG).show();
            }
        }else if(!origen.isPresent()){
            redirecciona = false;
            Toast.makeText(NuevaReservaActivity.this, "Debe de seleccionar un origen.", Toast.LENGTH_LONG).show();
        }

        if(redirecciona) {
            detailIntent.putExtra("VUELO_ORIGEN", origen.get());
            detailIntent.putExtra("USUARIO", usuario);
            startActivity(detailIntent);
        }
    }


}