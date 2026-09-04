package com.emprende.inti_agenda;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.util.List;

public class PedidosAdapter extends ArrayAdapter<Pedido> {

    public PedidosAdapter(Context context, List<Pedido> pedidos) {
        super(context, 0, pedidos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pedido, parent, false);
        }

        Pedido pedido = getItem(position);

        LinearLayout cardContainer = convertView.findViewById(R.id.cardContainer);
        TextView tvTitulo = convertView.findViewById(R.id.tvTitulo);
        TextView tvCliente = convertView.findViewById(R.id.tvCliente);
        TextView tvDescripcion = convertView.findViewById(R.id.tvDescripcion);
        TextView tvPago = convertView.findViewById(R.id.tvPago);
        TextView tvFechaEntrega = convertView.findViewById(R.id.tvFechaEntrega);

        if (pedido != null) {
            tvTitulo.setText(pedido.getTitulo());
            tvCliente.setText("Cliente: " + pedido.getCliente());
            tvDescripcion.setText("Desc: " + pedido.getDescripcion());
            tvPago.setText("Pago: " + pedido.getPago());
            tvFechaEntrega.setText("Entrega: " + pedido.getFechaEntrega());

            // Tinte dinámico de la tarjeta según el color seleccionado
            int colorCard = obtenerColorCategoria(pedido.getCategoria());
            Drawable background = ContextCompat.getDrawable(getContext(), R.drawable.bg_card).mutate();
            background.setTint(colorCard);
            cardContainer.setBackground(background);
        }

        return convertView;
    }

    private int obtenerColorCategoria(String categoria) {
        if (categoria == null) return Color.parseColor("#D47FA6");

        switch (categoria) {
            case "Categoria 1": // circulorosa (Rosa Fuerte)
                return Color.parseColor("#D47FA6");
            case "Categoria 2": // circuloturquesa (Turquesa)
                return Color.parseColor("#57B09A");
            case "Categoria 3": // circulorosa2 (Rosa Claro)
                return Color.parseColor("#EBB3CE");
            case "Categoria 4": // circuloverde2 (Verde Lima)
                return Color.parseColor("#C3D152");
            case "Categoria 5": // circuloverde (Verde Oliva)
                return Color.parseColor("#7CA879");
            default:
                return Color.parseColor("#D47FA6");
        }
    }
}