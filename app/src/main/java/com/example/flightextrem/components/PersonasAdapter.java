package com.example.flightextrem.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.flightextrem.R;
import com.example.flightextrem.activity.MainActivity;
import com.example.flightextrem.service.pojo.Extra;
import com.example.flightextrem.service.pojo.Viajero;
import com.example.flightextrem.service.pojo.Vuelo;
import com.google.android.material.card.MaterialCardView;

import java.time.LocalDate;
import java.util.List;

public class PersonasAdapter extends RecyclerView.Adapter<PersonasAdapter.ViewHolder> {
    private List<Viajero> mData;
    private Vuelo mVueloData;
    private LayoutInflater mInflater;
    private Context context;

    final PersonasAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Viajero item);
    }

    public PersonasAdapter(List<Viajero> itemList,
                           Vuelo itemVuelo,
                           Context context, PersonasAdapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.mVueloData = itemVuelo;
        this.listener = listener;

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public PersonasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_personas, null);
        return new PersonasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PersonasAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Viajero> viajeros) {
        mData = viajeros;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView nombre;
        TextView esAdulto;
        TextView apellidos;
        TextView recPrecioBillete;
        LocalDate nacimiento;
        String dni;
        MaterialCardView materialCardView;
        ImageView close;

        ViewHolder(View vista) {
            super(vista);
            nombre = vista.findViewById(R.id.recNombrePasajero);
            esAdulto = vista.findViewById(R.id.recEsAdulto);
            materialCardView = vista.findViewById(R.id.comboExtras);
            recPrecioBillete = vista.findViewById(R.id.recPrecioBillete);
            close = vista.findViewById(R.id.recButtonClose);
        }

        void bindData(final Viajero viajero) {
            nombre.setText(viajero.getNombre().concat(" ").concat(viajero.getApellidos()));
            esAdulto.setText("Adulto");
            recPrecioBillete.setText(mVueloData.getPrecio().toString());
            close.setTag(viajero);
            close.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mData.remove(v.getTag());
                    notifyDataSetChanged();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(viajero);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }
    }


}
