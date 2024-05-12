package com.example.flightextrem.components;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.flightextrem.R;
import com.example.flightextrem.activity.DetailActivity;
import com.example.flightextrem.activity.HomeActivity;
import com.example.flightextrem.activity.MainActivity;
import com.example.flightextrem.service.pojo.Oferta;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class OfertasAdapter extends RecyclerView.Adapter<OfertasAdapter.ViewHolder> {
    private List<Oferta> mData;
    private LayoutInflater mInflater;
    private Context context;
    final OfertasAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Oferta item);

    }

    public OfertasAdapter(List<Oferta> itemList, Context context, OfertasAdapter.OnItemClickListener listener) {
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
    public OfertasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_ofertas, null);
        return new OfertasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OfertasAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Oferta> ofertas) {
        mData = ofertas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView icon;
        TextView descripcion;
        TextView fechaFin;
        Button irOferta;

        ViewHolder(View vista) {
            super(vista);
            icon = vista.findViewById(R.id.icOferta);
            descripcion = vista.findViewById(R.id.textOfferDescription);
            fechaFin = vista.findViewById(R.id.recFechaSalida);
            irOferta = vista.findViewById(R.id.ir_button);
        }

        void bindData(final Oferta ofertas) {
            descripcion.setText(ofertas.getDescripcion());
            irOferta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("VUELO", ofertas.getVuelo());
                    v.getContext().startActivity(intent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(ofertas);
                }
            });
        }

    }


}
