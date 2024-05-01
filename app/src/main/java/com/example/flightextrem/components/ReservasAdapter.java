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

public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.ViewHolder> {
    private List<ListReservas> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ReservasAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListReservas item);
    }

    public ReservasAdapter(List<ListReservas> itemList, Context context, ReservasAdapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ReservasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_reservas_card, null);
        return new ReservasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReservasAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListReservas> vuelos) {
        mData = vuelos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView icon;
        TextView idReserva;
        TextView precio;
        TextView fechaReserva;

        ViewHolder(View vista) {
            super(vista);
            icon = vista.findViewById(R.id.icReserva);
            idReserva = vista.findViewById(R.id.recIdReserva);
            precio = vista.findViewById(R.id.recPrecioReserva);
            fechaReserva = vista.findViewById(R.id.recFechaReserva);
        }

        void bindData(final ListReservas reservas) {
            idReserva.setText(reservas.getIdReserva());
            precio.setText(reservas.getTotal().toString().concat(" EUR"));
            fechaReserva.setText(reservas.getFechaReserva().toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(reservas);
                }
            });
        }

    }


}
