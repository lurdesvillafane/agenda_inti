package com.emprende.inti_agenda;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerPedidosActivity extends AppCompatActivity {

    private ListView listView;
    private PedidosAdapter adapter;
    private List<Pedido> listaPedidos;

    private int userId = 0;
    private String fechaConsulta = "";

    // URLs centralizadas desde Constants
    private static final String URL_OBTENER = Constants.BASE_URL + "obtener_pedidos.php";
    private static final String URL_ELIMINAR = Constants.BASE_URL + "eliminar_pedido.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_pedidos);

        listView = findViewById(R.id.listapedidos);
        ImageView btnVolver = findViewById(R.id.btnVolver);

        listaPedidos = new ArrayList<>();
        adapter = new PedidosAdapter(this, listaPedidos);
        listView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int dia = bundle.getInt("dia", 1);
            int mes = bundle.getInt("mes", 1);
            int anio = bundle.getInt("anio", 2026);
            userId = bundle.getInt("USER_ID", 0);

            fechaConsulta = String.format("%04d-%02d-%02d", anio, mes, dia);
        }

        cargarPedidosServidor();

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Pedido pedidoSeleccionado = listaPedidos.get(position);
            mostrarDialogoEliminar(pedidoSeleccionado.getId(), position);
            return true;
        });

        btnVolver.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargarPedidosServidor() {
        String urlConParametros = URL_OBTENER + "?user_id=" + userId + "&fecha=" + fechaConsulta;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlConParametros,
                null,
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            JSONArray arrayPedidos = response.getJSONArray("pedidos");
                            listaPedidos.clear();

                            for (int i = 0; i < arrayPedidos.length(); i++) {
                                JSONObject obj = arrayPedidos.getJSONObject(i);
                                int idPedido = obj.getInt("id");
                                String titulo = obj.optString("titulo", "");
                                String cliente = obj.optString("cliente", "");
                                String desc = obj.optString("descripcion", "");
                                String pago = obj.optString("pago", "");
                                String fechaEntrega = obj.optString("fecha_entrega", "");
                                String categoriaColor = obj.optString("categoria_color", "Categoria 1");

                                listaPedidos.add(new Pedido(idPedido, titulo, cliente, desc, pago, fechaEntrega, categoriaColor));
                            }

                            adapter.notifyDataSetChanged();

                            if (listaPedidos.isEmpty()) {
                                Toast.makeText(this, "No hay pedidos para este día", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void mostrarDialogoEliminar(int idPedido, int posicionLista) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar pedido")
                .setMessage("¿Deseas eliminar este pedido definitivamente?")
                .setPositiveButton("Eliminar", (dialog, which) -> eliminarPedidoServidor(idPedido, posicionLista))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarPedidoServidor(int idPedido, int posicionLista) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_ELIMINAR,
                response -> {
                    Toast.makeText(this, "Pedido eliminado correctamente", Toast.LENGTH_SHORT).show();
                    listaPedidos.remove(posicionLista);
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error al eliminar el pedido", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(idPedido));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}