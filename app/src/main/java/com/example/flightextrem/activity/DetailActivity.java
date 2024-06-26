package com.example.flightextrem.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.collection.ArraySet;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightextrem.R;
import com.example.flightextrem.components.PersonasAdapter;
import com.example.flightextrem.service.ApiService;
import com.example.flightextrem.service.HTTPClientRetrofit;
import com.example.flightextrem.service.pojo.Extra;
import com.example.flightextrem.service.pojo.Oferta;
import com.example.flightextrem.service.pojo.Reserva;
import com.example.flightextrem.service.pojo.ReservaExtra;
import com.example.flightextrem.service.pojo.ReservaViajero;
import com.example.flightextrem.service.pojo.Usuario;
import com.example.flightextrem.service.pojo.Viajero;
import com.example.flightextrem.service.pojo.Vuelo;
import com.example.flightextrem.utils.JsonConverter;
import com.example.flightextrem.utils.Utiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.card.MaterialCardView;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout home, nueva, reservas, datos, about, exit;
    CardView cardDestino;
    Usuario usuario;
    Vuelo vueloIda;
    Vuelo destino;
    Oferta oferta;
    List<Reserva> reservaList;
    List<Viajero> personas;
    List<Extra> extras;
    List<Extra> extrasSelect = new ArrayList<>();
    TextView fechaNacimiento, txtHoraSalida, txtHoraDestino, txtFechaSalida, txtFechaDestino, txtDuracion,
            txtHoraOrigenVuelta, txtHoraDestinoVuelta, txtFechaSalidaVuelta, txtFechaDestinoVuelta, txtDuracionVuelta,
            txtViajeroNombre, txtViajeroApellidos, txtFechaNacimiento, txtViajeroDni, listaExtras, totalReserva;
    RecyclerView listaPasajeros;
    MaterialCardView materialCardView;
    private HTTPClientRetrofit httpClientRetrofit;

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("USUARIO", usuario);
        activity.startActivity(intent);
        activity.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        menu = findViewById(R.id.menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        home = findViewById(R.id.home);
        nueva = findViewById(R.id.nuevaReserva);
        reservas = findViewById(R.id.reservas);
        datos = findViewById(R.id.datos);
        about = findViewById(R.id.about);
        exit = findViewById(R.id.exit);
        cardDestino = findViewById(R.id.cardDestino);
        listaPasajeros = findViewById(R.id.listaPasajeros);
        txtHoraSalida = findViewById(R.id.txtHoraSalida);
        txtHoraDestino = findViewById(R.id.txtHoraDestino);
        txtFechaSalida = findViewById(R.id.txtFechaSalida);
        txtFechaDestino = findViewById(R.id.txtFechaDestino);
        txtDuracion = findViewById(R.id.txtDuracion);
        txtHoraOrigenVuelta = findViewById(R.id.txtHoraOrigenVuelta);
        txtHoraDestinoVuelta = findViewById(R.id.txtHoraDestinoVuelta);
        txtFechaSalidaVuelta = findViewById(R.id.txtFechaSalidaVuelta);
        txtFechaDestinoVuelta = findViewById(R.id.txtFechaDestinoVuelta);
        txtDuracionVuelta = findViewById(R.id.txtDuracionVuelta);
        txtViajeroNombre = findViewById(R.id.txtViajeroNombre);
        txtViajeroApellidos = findViewById(R.id.txtViajeroApellidos);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtViajeroDni = findViewById(R.id.txtViajeroDni);
        materialCardView = findViewById(R.id.comboExtras);
        listaExtras = findViewById(R.id.listaExtras);
        totalReserva = findViewById(R.id.totalReserva);
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
                redirectActivity(DetailActivity.this, HomeActivity.class);
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
                redirectActivity(DetailActivity.this, ReservasActivity.class);
            }
        });
        datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(DetailActivity.this, DatosActivity.class);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(DetailActivity.this, AboutActivity.class);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(DetailActivity.this, MainActivity.class);
            }
        });
        materialCardView.setOnClickListener(v -> {
            showDialog(v.getRootView());
        });

        usuario = getUsuarioContext();

        initDatePickers();
        init();
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

    /**
     * para obtener el parámetro de la oferta seleccionada del contexto
     *
     * @return Usuario
     */
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
    public void init() {
        vueloIda = recogeParametro("VUELO_ORIGEN");
        destino = recogeParametro("VUELO_DESTINO");
        oferta = getOfertaContext();
        if(oferta!=null){
            vueloIda = oferta.getVuelo();
        }
        personas = new ArrayList<>();
        extras = new ArrayList<>();

        cargaOrigen(vueloIda);
        cargaDestino(destino);
        obtenerVuelos(vueloIda);
        cargarExtras();
    }

    private void cargarExtras() {
        JsonConverter<Extra[]> jsonConverter = new JsonConverter(Extra[].class);
        ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);
        apiService.callExtras().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        extras = Arrays.asList(jsonConverter.jsonToObject(response.body()).clone());
                    } catch (Exception e) {
                        Toast.makeText(DetailActivity.this, "Se ha producido un error en la carga de extras.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Se ha producido un error en la carga de extras.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void cargaOrigen(Vuelo origen) {
        //if(origen != null){
            txtHoraSalida.setText(origen.getHoraSalida().toString() + " " + origen.getOrigen().getNombre());
            txtHoraDestino.setText(origen.getHoraLlegada().toString() + " " + origen.getDestino().getNombre());
            txtFechaSalida.setText(origen.getFechaSalida().toString());
            txtFechaDestino.setText(origen.getFechaSalida().toString());
            txtDuracion.setText("Duración del vuelo: " + Duration.between(origen.getHoraSalida(), origen.getHoraLlegada()).toHours() + " h");
        //}
    }

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void cargaDestino(Vuelo destino) {
        if (destino == null)
            cardDestino.setVisibility(View.INVISIBLE);
        else {
            txtHoraOrigenVuelta.setText(destino.getHoraSalida().toString() + " " + destino.getOrigen().getNombre());
            txtHoraDestinoVuelta.setText(destino.getHoraLlegada().toString() + " " + destino.getDestino().getNombre());
            txtFechaSalidaVuelta.setText(destino.getFechaSalida().toString());
            txtFechaDestinoVuelta.setText(destino.getFechaSalida().toString());
            txtDuracionVuelta.setText("Duración del vuelo: " + Duration.between(destino.getHoraSalida(), destino.getHoraLlegada()).toHours() + " h");
        }
    }

    private Vuelo recogeParametro(String param) {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        } else {
            return (Vuelo) extras.get(param);
        }
    }

    /**
     * Para cargar los datepickers de la aplicación
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initDatePickers() {
        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtenemos la instancia de calendar
                final Calendar c = Calendar.getInstance();

                // obtenemos el día el mes y el año actual, para cuando abramos el datepicker se lo podamos establecer
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                fechaNacimiento.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        // el año mes y día actual se lo pasamos al datepiker
                        year, month, day);
                // mostramos el datepicker
                datePickerDialog.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void aniadirPasajero(View view) {
        String errores = "";
        Boolean correcto = true;
        String dni = txtViajeroDni.getText().toString();
        if (dni.isEmpty()) {
            errores += "El campo dni es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarDNI(dni)) {
                errores += "El dni no tiene el formato correcto.\n";
                correcto = false;
            }
        }

        String nombre = txtViajeroNombre.getText().toString();
        if (nombre.isEmpty()) {
            errores += "El campo nombre es obligatorio.\n";
            correcto = false;
        } else {
            if (Utiles.validarSiNumero(nombre)) {
                errores += "Escriba el nombre correctamente.\n";
                correcto = false;
            }
        }

        String apellido = txtViajeroApellidos.getText().toString();
        if (apellido.isEmpty()) {
            errores += "El campo apellido es obligatorio.\n";
            correcto = false;
        } else {
            if (Utiles.validarSiNumero(apellido)) {
                errores += "Escriba los apellidos correctamente.\n";
                correcto = false;
            }
        }

        String fechaNacimiento = txtFechaNacimiento.getText().toString();
        LocalDate fecha = null;
        if (fechaNacimiento.isEmpty()) {
            errores += "El campo fecha nacimiento es obligatorio.\n";
            correcto = false;
        } else {
            if (!Utiles.validarPatron(fechaNacimiento,"([0-9]{2,})([/])([0-9]{2,})([/])([0-9]{4,})")) {
                errores += "El formato de la fecha no es correcto.\n";
                correcto = false;
            } else {
                fecha = Utiles.convertirADate(fechaNacimiento);
            }
        }

        if (correcto) {
            personas.add(new Viajero(null, dni, nombre, apellido, fecha));
            PersonasAdapter adapter = new PersonasAdapter(personas, vueloIda, this, new PersonasAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Viajero item) {
                    calcularTotalReserva();
                }
            });
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    calcularTotalReserva();
                }
            });
            RecyclerView recyclerView = findViewById(R.id.listaPasajeros);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            calcularTotalReserva();
        } else {
            Toast.makeText(DetailActivity.this, errores, Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Seleccione los extras");

        builder.setCancelable(false);
        if (android.os.Build.VERSION.SDK_INT >= 20) {
            List<String> extrasStr = new ArrayList<>();
            extras.forEach(extra -> {
                extrasStr.add(extra.toString());
            });

            builder.setItems(extrasStr.toArray(new String[extrasStr.size()]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Extra extraSelec = extras.get(which);
                    if(!extrasSelect.contains(extraSelec)) {
                        listaExtras.setText(listaExtras.getText().toString().concat(extraSelec.getNombre().concat("; ")));
                        extrasSelect.add(extraSelec);
                        calcularTotalReserva();
                    }else{
                        Toast.makeText(DetailActivity.this, "El extra seleccionado ya existe en la lista de selección.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Double calcularTotalReserva() {
        Double total = new Double(0);

        // calculamos primero el total de los billetes simples
        total += (vueloIda.getPrecio() + (destino != null ? destino.getPrecio() : new Double(0))) * personas.size();
        // despues vamos recorriendo la lista de extras y se las sumamos al total
        for (Extra extra : extrasSelect) {
            total += extra.getCoste();
        }
        if(oferta!=null)
            total = total - (total * oferta.getDescuento());

        totalReserva.setText(total.toString().concat(" EUR"));
        return total;
    }

    /**
     * Guarda la reserva realizada por el usuario
     * @param view
     * @throws JsonProcessingException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void reservar(View view) throws JsonProcessingException {

        Set<ReservaExtra> reservaExtraSet = new ArraySet<>();
        Double total = this.calcularTotalReserva();
        for (Extra extra : extrasSelect) {
            reservaExtraSet.add(new ReservaExtra(null, null, extra));
        }
        Set<ReservaViajero> reservaViajeroSet = new ArraySet<>();
        for (Viajero persona : personas) {
            reservaViajeroSet.add(new ReservaViajero(null, null, persona));
        }
        // generar la reserva y llamar a la api para guardarla en base de datos
        Reserva reserva = new Reserva(null, Utiles.generarCodigoReservas(), usuario, vueloIda, destino, oferta, personas.size(), total,
                reservaExtraSet, null, reservaViajeroSet);
        if(compruebaDisponibilidad(reserva)) {
            JsonConverter<Reserva> converter = new JsonConverter<>(Reserva.class);
            converter.objectToJson(reserva);
            ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);
            apiService.callInsertarReserva(reserva).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (call.isExecuted() && response.body() != null) {
                        Toast.makeText(DetailActivity.this, "La reserva se ha registrado correctamente.", Toast.LENGTH_LONG).show();
                        // aquí hay que llamar a la api para generar el código QR de la reserva
                    } else {
                        Toast.makeText(DetailActivity.this, "Ha ocurrido un problema con el registro de la reserva.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "Ha ocurrido un problema con el registro de la reserva.", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    /**
     * Comprueba la disponibilidad de la reserva
     * @param reserva
     * @return
     */
    @SneakyThrows
    private boolean compruebaDisponibilidad(Reserva reserva) {
        boolean disponible = true;
        Long totalViajeros = 0L;
        for(Reserva r : reservaList){
            totalViajeros += r.getReservaViajeros().stream().count();
        }
        if(reserva.getVueloIda().getAvion().getPasajeros() < totalViajeros + reserva.getReservaViajeros().size()){
            Toast.makeText(DetailActivity.this, "Se ha superado el límite de pasajeros para este vuelo.", Toast.LENGTH_LONG).show();
            disponible = false;
        }

        return disponible;
    }

    private void obtenerVuelos(Vuelo vuelo) {
        JsonConverter<Reserva[]> converter = new JsonConverter<>(Reserva[].class);

        ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);
        apiService.callReservas(new Reserva(vuelo)).enqueue(new Callback<String>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call.isExecuted() && response.body() != null) {
                    reservaList = Arrays.asList(converter.jsonToObject(response.body()));
                } else {
                    Toast.makeText(DetailActivity.this, "Ha ocurrido un problema con la disponibilidad del vuelo.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Ha ocurrido un problema con la disponibilidad del vuelo.", Toast.LENGTH_LONG).show();
            }

        });
    }

}