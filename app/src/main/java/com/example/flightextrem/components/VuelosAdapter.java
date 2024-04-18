package com.example.flightextrem.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.flightextrem.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class VuelosAdapter extends RecyclerView.Adapter<VuelosAdapter.ViewHolder> {
    private List<ListVuelos> mData;
    private LayoutInflater mInflater;
    private Context context;

    public VuelosAdapter(List<ListVuelos> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public VuelosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= mInflater.inflate(R.layout.activity_vuelos, null);
        return new VuelosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VuelosAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListVuelos> vuelos){ mData= vuelos;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView icon;
        TextView origenDestino;
        TextView precio;
        TextView fechaSalida;

        ViewHolder(View vista){
            super(vista);
            icon = vista.findViewById(R.id.icPlane);
            origenDestino = vista.findViewById(R.id.recOrigenDestino);
            precio = vista.findViewById(R.id.recPrecio);
            fechaSalida = vista.findViewById(R.id.recFechaSalida);
        }

        void bindData(final ListVuelos vuelos){
            origenDestino.setText(vuelos.getOrigen().concat("/").concat(vuelos.getDestino()));
            precio.setText(vuelos.getPrecio().toString());
            fechaSalida.setText(vuelos.getFechaSalida().toString());
        }

    }


}
