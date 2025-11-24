package com.example.ejroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClaseAdapter extends RecyclerView.Adapter<ClaseAdapter.ClaseViewHolder> {
    private List<Clase> lista;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Clase clase);
    }

    public ClaseAdapter(List<Clase> lista, OnItemClickListener listener) {
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
        holder.txtNombre.setText(c.nombre);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(c));
    }

    @Override
    public int getItemCount() { return lista == null ? 0 : lista.size(); }

    public void setClases(List<Clase> clases) {
        this.lista = clases;
        notifyDataSetChanged();
    }

    static class ClaseViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;
        public ClaseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreClase);
        }
    }
}