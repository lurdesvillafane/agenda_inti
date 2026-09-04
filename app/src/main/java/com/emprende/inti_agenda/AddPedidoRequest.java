package com.emprende.inti_agenda;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddPedidoRequest extends StringRequest {

    private static final String URL_PHP = Constants.BASE_URL + "agregar_pedido.php";
    private final Map<String, String> params;

    public AddPedidoRequest(int idUsuario, String userName, String titulo, String descripcion, String pago,
                            String fechaInicio, String fechaEntrega, String cliente,
                            String categoriaColor, Response.Listener<String> listener,
                            Response.ErrorListener errorListener) {
        super(Method.POST, URL_PHP, listener, errorListener);

        params = new HashMap<>();
        params.put("user_id", String.valueOf(idUsuario));
        params.put("user_name", userName);
        params.put("titulo", titulo);
        params.put("descripcion", descripcion);
        params.put("pago", pago);
        params.put("fecha_inicio", fechaInicio);
        params.put("fecha_entrega", fechaEntrega);
        params.put("cliente", cliente);
        params.put("categoria_color", categoriaColor);
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}