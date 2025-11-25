package com.example.ejroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClaseAdapter extends RecyclerView.Adapter<ClaseAdapter.ClaseViewHolder> {
    private List<Clase> lista;
    private final OnClaseActionListener listener;

    // Interfaz para gestionar las 3 acciones
    public interface OnClaseActionListener {
        void onNavegar(Clase clase); // Click en "play" para entrar
        void onModificar(Clase clase, String nuevoNombre); // Click en "Modif"
        void onBorrar(Clase clase); // Click en "X"
    }

    public ClaseAdapter(List<Clase> lista, OnClaseActionListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_clase, parent, false);
        return new ClaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaseViewHolder holder, int position) {
        Clase c = lista.get(position);
        holder.etNombre.setText(c.nombre);

        // 1. Botón Entrar (Navegar)
        holder.btnEntrar.setOnClickListener(v -> listener.onNavegar(c));

        // 2. Botón Modificar
        holder.btnModificar.setOnClickListener(v -> {
            listener.onModificar(c, holder.etNombre.getText().toString());
        });

        // 3. Botón Borrar
        holder.btnBorrar.setOnClickListener(v -> listener.onBorrar(c));
    }

    @Override
    public int getItemCount() { return lista == null ? 0 : lista.size(); }

    public void setClases(List<Clase> clases) {
        this.lista = clases;
        notifyDataSetChanged();
    }

    static class ClaseViewHolder extends RecyclerView.ViewHolder {
        EditText etNombre;
        Button btnModificar, btnBorrar;
        Button btnEntrar;

        public ClaseViewHolder(@NonNull View itemView) {
            super(itemView);
            etNombre = itemView.findViewById(R.id.etNombreClase);
            btnModificar = itemView.findViewById(R.id.btnModificarClase);
            btnBorrar = itemView.findViewById(R.id.btnBorrarClase);
            btnEntrar = itemView.findViewById(R.id.btnEntrarClase);
        }
    }
}