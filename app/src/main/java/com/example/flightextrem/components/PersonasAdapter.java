package com.example.flightextrem.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.flightextrem.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.LocalDate;
import java.util.List;

public class PersonasAdapter extends RecyclerView.Adapter<PersonasAdapter.ViewHolder> {
    private List<ListPersonas> mData;
    private LayoutInflater mInflater;
    private Context context;
    final PersonasAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListPersonas item);
    }

    public PersonasAdapter(List<ListPersonas> itemList, Context context, PersonasAdapter.OnItemClickListener listener) {
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
    public PersonasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_personas, null);
        return new PersonasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PersonasAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListPersonas> personas) {
        mData = personas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView icon;
        TextView nombre;
        TextView esAdulto;
        TextView apellidos;
        LocalDate nacimiento;
        String dni;

        ViewHolder(View vista) {
            super(vista);
            icon = vista.findViewById(R.id.icPasajero);
            nombre = vista.findViewById(R.id.recNombrePasajero);
            esAdulto = vista.findViewById(R.id.recEsAdulto);
        }

        void bindData(final ListPersonas personas) {
            nombre.setText(personas.getNombre().concat(" ").concat(personas.getApellidos()));
            esAdulto.setText("Adulto");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(personas);
                }
            });
        }

    }


}
