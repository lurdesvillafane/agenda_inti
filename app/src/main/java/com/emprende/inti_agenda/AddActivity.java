package com.emprende.inti_agenda;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private EditText edtNombrePedido, descripcion, pago, fechadesde, fechahasta, etnombreCliente;
    private Button buttonGuardar;
    private ImageView btnVolver;
    private String categoriaColorSeleccionada = "Categoria 1"; // Color/Categoría por defecto

    // VARIABLES PARA IDENTIFICAR AL USUARIO
    private String usuarioLogueado = "";
    private int idUsuario = 0; // Agregado para vincular con la clave foránea user_id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Binding de vistas
        edtNombrePedido = findViewById(R.id.edtNombrePedido);
        descripcion = findViewById(R.id.Descripcion);
        pago = findViewById(R.id.Pago);
        fechadesde = findViewById(R.id.fechadesde);
        fechahasta = findViewById(R.id.fechahasta);
        etnombreCliente = findViewById(R.id.etnombreCliente);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        btnVolver = findViewById(R.id.btnVolver);

        // Obtener datos enviados desde HomeActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int dia = bundle.getInt("dia", 1);
            int mes = bundle.getInt("mes", 1);
            int anio = bundle.getInt("anio", 2026);

            // Recepción del usuario e ID
            usuarioLogueado = bundle.getString("usuario", "");
            idUsuario = bundle.getInt("user_id", 0);

            String fechaInic = String.format("%04d-%02d-%02d", anio, mes, dia);
            fechadesde.setText(fechaInic);
            fechahasta.setText(fechaInic);
        }

        // Configuración de DatePickerDialog para selección táctil de fechas
        fechadesde.setOnClickListener(v -> mostrarDatePicker(fechadesde));
        fechahasta.setOnClickListener(v -> mostrarDatePicker(fechahasta));

        btnVolver.setOnClickListener(v -> finish());
        buttonGuardar.setOnClickListener(v -> guardarPedidoServidor());

        // Manejo de selección visual de colores / categorías
        ImageView circulorosa = findViewById(R.id.circulorosa);
        ImageView circuloturquesa = findViewById(R.id.circuloturquesa);
        ImageView circulorosa2 = findViewById(R.id.circulorosa2);
        ImageView circuloverde2 = findViewById(R.id.circuloverde2);
        ImageView circuloverde = findViewById(R.id.circuloverde);

        List<ImageView> colors = Arrays.asList(
                circulorosa, circuloturquesa, circulorosa2, circuloverde2, circuloverde
        );

        for (ImageView img : colors) {
            img.setOnClickListener(v -> {
                for (ImageView colorView : colors) {
                    colorView.setBackground(null);
                }
                v.setBackgroundResource(R.drawable.circulogris);

                // Mapeo según el color seleccionado
                int id = v.getId();
                if (id == R.id.circulorosa) categoriaColorSeleccionada = "Categoria 1";
                else if (id == R.id.circuloturquesa) categoriaColorSeleccionada = "Categoria 2";
                else if (id == R.id.circulorosa2) categoriaColorSeleccionada = "Categoria 3";
                else if (id == R.id.circuloverde2) categoriaColorSeleccionada = "Categoria 4";
                else if (id == R.id.circuloverde) categoriaColorSeleccionada = "Categoria 5";
            });
        }
    }

    private void mostrarDatePicker(EditText campoTexto) {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String fechaSeleccionada = String.format("%04d-%02d-%02d", year, (monthOfYear + 1), dayOfMonth);
                    campoTexto.setText(fechaSeleccionada);
                }, anio, mes, dia);
        datePickerDialog.show();
    }

    private void guardarPedidoServidor() {
        String titulo = edtNombrePedido.getText().toString().trim();
        String desc = descripcion.getText().toString().trim();
        String datosPago = pago.getText().toString().trim();
        String fInicio = fechadesde.getText().toString().trim();
        String fEntrega = fechahasta.getText().toString().trim();
        String cliente = etnombreCliente.getText().toString().trim();

        // Validaciones previas al envío
        if (titulo.isEmpty()) {
            Toast.makeText(this, "Complete el nombre del pedido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (idUsuario == 0) {
            Toast.makeText(this, "Error: No se identificó la sesión del usuario", Toast.LENGTH_LONG).show();
            return;
        }

        // Petición Volley enviando idUsuario como primer parámetro
        AddPedidoRequest addPedidoRequest = new AddPedidoRequest(
                idUsuario, usuarioLogueado, titulo, desc, datosPago, fInicio, fEntrega, cliente, categoriaColorSeleccionada,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("success")) {
                            Toast.makeText(AddActivity.this, "¡Pedido guardado con éxito!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            String detalleError = jsonObject.optString("error", "Error desconocido");
                            Toast.makeText(AddActivity.this, "Error BD: " + detalleError, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(AddActivity.this, "Error de respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(AddActivity.this, "Error de red/servidor: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(addPedidoRequest);
    }
}