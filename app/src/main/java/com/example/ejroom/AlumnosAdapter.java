package com.example.ejroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.AlumnoViewHolder> {

    private List<Alumno> listaAlumnos;
    // Interfaces para gestionar los clics en los botones de cada fila
    private final OnItemClickListener listenerModificar;
    private final OnItemClickListener listenerBorrar;

    public interface OnItemClickListener {
        void onItemClick(Alumno alumno, String nuevoNombre, float nuevaNota);
    }

    public AlumnosAdapter(List<Alumno> listaAlumnos, OnItemClickListener modificar, OnItemClickListener borrar) {
        this.listaAlumnos = listaAlumnos;
        this.listenerModificar = modificar;
        this.listenerBorrar = borrar;
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_alumno, parent, false);
        return new AlumnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        Alumno alumno = listaAlumnos.get(position);

        holder.etNombre.setText(alumno.getNombre());
        holder.etNota.setText(String.valueOf(alumno.getNota()));

        holder.btnModificar.setOnClickListener(v -> {
            String nuevoNombre = holder.etNombre.getText().toString();
            float nuevaNota = Float.parseFloat(holder.etNota.getText().toString());
            listenerModificar.onItemClick(alumno, nuevoNombre, nuevaNota);
        });

        holder.btnBorrar.setOnClickListener(v -> {
            listenerBorrar.onItemClick(alumno, null, 0);
        });
    }

    @Override
    public int getItemCount() {
        return listaAlumnos != null ? listaAlumnos.size() : 0;
    }

    public void setAlumnos(List<Alumno> alumnos){
        this.listaAlumnos = alumnos;
        notifyDataSetChanged(); // Avisa a la lista que se redibuje
    }

    static class AlumnoViewHolder extends RecyclerView.ViewHolder {
        EditText etNombre, etNota;
        Button btnModificar, btnBorrar;

        public AlumnoViewHolder(@NonNull View itemView) {
            super(itemView);
            etNombre = itemView.findViewById(R.id.etNombre);
            etNota = itemView.findViewById(R.id.etNota);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnBorrar = itemView.findViewById(R.id.btnBorrar);
        }
    }
}