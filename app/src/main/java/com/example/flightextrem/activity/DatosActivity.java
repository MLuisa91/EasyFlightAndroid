package com.example.flightextrem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.flightextrem.R;
import com.example.flightextrem.service.ApiService;
import com.example.flightextrem.service.HTTPClientRetrofit;
import com.example.flightextrem.service.pojo.Pais;
import com.example.flightextrem.service.pojo.Rol;
import com.example.flightextrem.service.pojo.Usuario;
import com.example.flightextrem.service.pojo.UsuarioRol;
import com.example.flightextrem.utils.JsonConverter;
import com.example.flightextrem.utils.Utiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosActivity extends AppCompatActivity {

    ImageView menu;
    TextView usuarioLogin;
    DrawerLayout drawerLayout;
    LinearLayout home, nueva, reservas, datos, about, exit;
    EditText nombre, apellidos, email, telefono, user, password, dni;
    Spinner pais;
    private HTTPClientRetrofit httpClientRetrofit;
    private static Usuario usuarioContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        nombre = findViewById(R.id.txtNombreUsuario);
        apellidos = findViewById(R.id.txtApellidos);
        email = findViewById(R.id.txtEmail);
        telefono = findViewById(R.id.txtTelefono);
        user = findViewById(R.id.txtUsuario);
        password = findViewById(R.id.txtPassword);
        pais = findViewById(R.id.spnPaises);
        dni = findViewById(R.id.txtDNI);
        httpClientRetrofit = new HTTPClientRetrofit();
        usuarioLogin = findViewById(R.id.txtUsuarioLogin);

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
                redirectActivity(DatosActivity.this, HomeActivity.class);
            }
        });
        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(DatosActivity.this, NuevaReservaActivity.class);
            }
        });
        reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(DatosActivity.this, ReservasActivity.class);
            }
        });
        datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(DatosActivity.this, AboutActivity.class);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(DatosActivity.this, MainActivity.class);
            }
        });

        initSpinners(pais);
        usuarioContext = getUsuarioContext();
        if (usuarioContext != null){
            usuarioLogin.setText("Bienvenido, " + usuarioContext.getNombre().concat(" ").concat(usuarioContext.getApellidos()));
            cargarPantalla(usuarioContext);
        }
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

    private void initSpinners(Spinner pais) {
        JsonConverter<Pais[]> jsonConverter = new JsonConverter(Pais[].class);
        ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);
        apiService.callPaises().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        List<Pais> paises = new ArrayList<>();
                        paises = Arrays.asList(jsonConverter.jsonToObject(response.body()).clone());
                        ArrayAdapter arrayAdapterPaises = new ArrayAdapter<>(DatosActivity.this, android.R.layout.simple_spinner_item, paises);
                        arrayAdapterPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        pais.setAdapter(arrayAdapterPaises);
                    } catch (Exception e) {
                        Toast.makeText(DatosActivity.this, "Se ha producido un error en la carga de paises.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DatosActivity.this, "Se ha producido un error en la carga de paises.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarPantalla(Usuario usuario) {

        if (usuario.getNombre() != null)
            this.nombre.setText(usuario.getNombre());

        if (usuario.getApellidos() != null)
            this.apellidos.setText(usuario.getApellidos());

        if (usuario.getDni() != null)
            this.dni.setText(usuario.getDni());

        if (usuario.getEmail() != null)
            this.email.setText(usuario.getEmail());

        if (usuario.getTelefono() != null)
            this.telefono.setText(usuario.getTelefono());

        if (usuario.getUser() != null)
            this.user.setText(usuario.getUser());

        if (usuario.getPassword() != null)
            this.password.setText(usuario.getPassword());


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


    public Usuario recogerDatos() {
        Usuario usuario = null;
        boolean correcto = true;
        Integer id = null;
        if (usuarioContext != null)
            id = usuarioContext.getId();

        String nombreUsuario = nombre.getText().toString();
        if (nombreUsuario.isEmpty()) {
            correcto = false;
        }
        String dniUsuario = dni.getText().toString();
        if (dniUsuario.isEmpty()) {
            correcto = false;
        } else {
            if (!Utiles.validarDNI(dniUsuario)) {
                correcto = false;
            }
        }
        String apellidosUsuario = apellidos.getText().toString();
        if (apellidosUsuario.isEmpty()) {
            correcto = false;
        }

        String telefonoUsuario = telefono.getText().toString();
        if (telefonoUsuario.isEmpty()) {
            correcto = false;
        } else {
            if (!Utiles.validarTelefono(telefonoUsuario)) {
                correcto = false;
            }
        }

        String userUsuario = user.getText().toString();
        if (userUsuario.isEmpty()) {
            correcto = false;
        }

        String passwordUsuario = password.getText().toString();
        if (passwordUsuario.isEmpty()) {
            correcto = false;
        }

        String emailUsuario = email.getText().toString();
        if (emailUsuario.isEmpty()) {
            correcto = false;
        } else {
            if (!Utiles.validaEmail(emailUsuario)) {
                correcto = false;
            }
        }

        Pais paisUsuario = (Pais) pais.getSelectedItem();
        if (paisUsuario == null) {
            correcto = false;
        }

        Set<UsuarioRol> rol = new HashSet<>();
        rol.add(new UsuarioRol(usuarioContext, new Rol(2)));

        if (correcto) {
            usuario = new Usuario(id, dniUsuario, nombreUsuario, apellidosUsuario, userUsuario, Utiles.encriptarAMD5(passwordUsuario), emailUsuario, telefonoUsuario, paisUsuario, rol);
        } else {
            Toast.makeText(DatosActivity.this, "Existen errores al completar los campos", Toast.LENGTH_LONG).show();
        }

        return usuario;

    }

    public void enviarDatos(View view) {
        Usuario usuario = recogerDatos();
        ApiService apiService = httpClientRetrofit.getHttpRetrofitConection().create(ApiService.class);

        if (usuario != null)
            if (usuario.getId() != null) {
                apiService.callUsuarioById(usuario.getId()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                apiService.callActualizarUsuario(usuario).enqueue(new Callback<Usuario>() {
                                    @Override
                                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                        Toast.makeText(DatosActivity.this, "Se ha actualizado el usuario correctamente.", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Usuario> call, Throwable t) {
                                        Toast.makeText(DatosActivity.this, "Error al actualizar el usuario", Toast.LENGTH_LONG).show();
                                    }
                                });

                            } catch (Exception e) {
                                Toast.makeText(DatosActivity.this, "Error al consultar el usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(DatosActivity.this, "Se ha producido un error al actualizar el usuario.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                apiService.callInsertarUsuario(usuario).enqueue(new Callback<Usuario>() {

                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        Toast.makeText(DatosActivity.this, "El usuario ha sido creado correctamente.", Toast.LENGTH_SHORT).show();
                        irALogin();
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(DatosActivity.this, "Se ha producido un error al crear el usuario.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }

    private void irALogin() {
        Intent intent = new Intent(DatosActivity.this, MainActivity.class);
        startActivity(intent);
    }
}