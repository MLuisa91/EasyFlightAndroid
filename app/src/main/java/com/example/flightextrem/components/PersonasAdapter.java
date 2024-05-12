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
import com.google.android.material.card.MaterialCardView;

import java.time.LocalDate;
import java.util.List;

public class PersonasAdapter extends RecyclerView.Adapter<PersonasAdapter.ViewHolder> {
    private List<Viajero> mData;
    private List<Extra> mExtrasData;
    private LayoutInflater mInflater;
    private Context context;

    final PersonasAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Viajero item);
    }

    public PersonasAdapter(List<Viajero> itemList,
                           List<Extra> itemExtrasList,
                           Context context, PersonasAdapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.mExtrasData = itemExtrasList;
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
        LocalDate nacimiento;
        String dni;
        MaterialCardView materialCardView;
        ImageView close;

        ViewHolder(View vista) {
            super(vista);
            nombre = vista.findViewById(R.id.recNombrePasajero);
            esAdulto = vista.findViewById(R.id.recEsAdulto);
            materialCardView = vista.findViewById(R.id.comboExtras);
            close = vista.findViewById(R.id.recButtonClose);
        }

        void bindData(final Viajero viajero) {
            nombre.setText(viajero.getNombre().concat(" ").concat(viajero.getApellidos()));
            esAdulto.setText("Adulto");
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

            /*materialCardView.setOnClickListener(v -> {
                showDialog(v.getRootView());
            });*/
        }

        private void showDialog(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Seleccione los extras");

            builder.setCancelable(false);

            ArrayAdapter arrayAdapterPaises = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, mExtrasData);
            arrayAdapterPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            builder.setAdapter(arrayAdapterPaises, null);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setNeutralButton("Limpiar todo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();
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
