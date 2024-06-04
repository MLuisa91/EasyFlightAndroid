package com.example.flightextrem.components;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.flightextrem.R;
import com.example.flightextrem.service.pojo.Vuelo;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class VuelosAdapter extends RecyclerView.Adapter<VuelosAdapter.ViewHolder> {
    private List<Vuelo> mData;
    private LayoutInflater mInflater;
    private Context context;
    final VuelosAdapter.OnItemClickListener listener;
    Boolean esOrigen;

    public interface OnItemClickListener {
        void onItemClick(Vuelo item, Boolean esOrigen, Boolean seleccionado);

    }

    public VuelosAdapter(List<Vuelo> itemList, Boolean esOrigen, Context context, VuelosAdapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
        this.esOrigen = esOrigen;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public VuelosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_vuelos, null);
        return new VuelosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VuelosAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Vuelo> vuelos) {
        mData = vuelos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView icon;
        TextView origenDestino;
        TextView precio;
        TextView fechaSalida;
        TextView origenODestino;
        TextView seleccionado;

        ViewHolder(View vista) {
            super(vista);
            icon = vista.findViewById(R.id.icPlane);
            origenDestino = vista.findViewById(R.id.recOrigenDestino);
            precio = vista.findViewById(R.id.recPrecio);
            fechaSalida = vista.findViewById(R.id.recFechaSalida);
            origenODestino = vista.findViewById(R.id.recOrigenODestino);
            seleccionado = vista.findViewById(R.id.vueloSeleccionado);
        }

        void bindData(final Vuelo vuelos) {
            String origen = esOrigen ? "Origen" : "Destino";
            origenDestino.setText(vuelos.getOrigen().getNombre().concat("/").concat(vuelos.getDestino().getNombre()));
            precio.setText(vuelos.getPrecio().toString().concat(" EUR"));
            fechaSalida.setText(vuelos.getFechaSalida().toString());
            origenODestino.setText(origen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(seleccionado.getText().toString().equals("NO")){
                        itemView.setBackgroundColor(Color.parseColor("#407087"));
                        seleccionado.setText("SI");
                    }else{
                        itemView.setBackgroundColor(Color.WHITE);
                        seleccionado.setText("NO");
                    }
                    Boolean isSelected = seleccionado.getText().equals("NO") ? false : true;
                    listener.onItemClick(vuelos, esOrigen, isSelected);
                }
            });
        }

    }


}
