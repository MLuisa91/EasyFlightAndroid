package com.example.flightextrem.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.example.flightextrem.service.ApiService;
import com.example.flightextrem.service.HTTPClientRetrofit;
import com.example.flightextrem.service.pojo.Reserva;
import com.example.flightextrem.service.pojo.Usuario;
import com.example.flightextrem.utils.JsonConverter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasActivity extends AppCompatActivity {

    private static Usuario usuario;
    TextView usuarioLogin;
    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout home, nueva, reservas, datos, about, exit;
    List<Reserva> listReservas;
    HTTPClientRetrofit httpClientRetrofit;
    ImageView imageView;

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
        usuarioLogin = findViewById(R.id.txtUsuarioLogin);
        exit = findViewById(R.id.exit);
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

        usuario = getUsuarioContext();
        if (usuario != null)
            usuarioLogin.setText("Bienvenido, " + usuario.getNombre().concat(" ").concat(usuario.getApellidos()));

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

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init() {
        listReservas = new ArrayList<>();
        JsonConverter<Reserva[]> jsonConverter = new JsonConverter(Reserva[].class);
        ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);
        apiService.callReservaByUsuario(usuario.getId()).enqueue(new Callback<String>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listReservas = Arrays.asList(jsonConverter.jsonToObject(response.body()).clone());
                    ReservasAdapter adapter = new ReservasAdapter(listReservas, ReservasActivity.this, new ReservasAdapter.OnItemClickListener() {

                        @Override
                        public void onItemClick(Reserva item) {
                            generarCodigoQr(item);
                        }
                    });
                    RecyclerView recyclerView = findViewById(R.id.listaReservas);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ReservasActivity.this));
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ReservasActivity.this, "Se ha producido un error en la carga de las reservas.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ReservasActivity.this, "Se ha producido un error en la carga de las reservas.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método para generar el código QR y que abra una ventana modal y se muestre en esa ventana
     * @param reserva
     */
    private void generarCodigoQr(Reserva reserva) {
        try {
            String data = "Código reserva: " + reserva.getCode() + "\n"
                    + "Cliente: " + reserva.getUsuario().getNombre() + " " + reserva.getUsuario().getApellidos() + " " + reserva.getUsuario().getDni() + "\n"
                    + "Origen:  " + reserva.getVueloIda().getOrigen().getNombre() + "\n "
                    + "Destino: " + reserva.getVueloIda().getDestino().getNombre() + "\n"
                    + "Fecha Salida: " + reserva.getVueloIda().getFechaSalida();

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            BitMatrix matrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder encoder = new BarcodeEncoder();

            Bitmap imgBitmap = encoder.createBitmap(matrix);


            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialoglayout = inflater.inflate(R.layout.dialog_layout, null);

            imageView = dialoglayout.findViewById(R.id.idIVImage);
            imageView.setImageBitmap(imgBitmap);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialoglayout);
            builder.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}