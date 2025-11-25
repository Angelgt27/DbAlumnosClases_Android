package com.example.ejroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class FragmentListadoClases extends Fragment {
    private AlumnoViewModel viewModel;
    private ClaseAdapter adapter;
    private int cantidadClases = 0; // Variable para saber cuántas clases existen

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listado_alumnos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AlumnoViewModel.class);

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuración del Adapter (igual que antes)
        adapter = new ClaseAdapter(new ArrayList<>(), new ClaseAdapter.OnClaseActionListener() {
            @Override
            public void onNavegar(Clase clase) {
                Bundle bundle = new Bundle();
                bundle.putInt("claseId", clase.getId());
                Navigation.findNavController(view).navigate(R.id.action_clases_to_alumnos, bundle);
            }

            @Override
            public void onModificar(Clase clase, String nuevoNombre) {
                if(!nuevoNombre.isEmpty()){
                    clase.nombre = nuevoNombre;
                    viewModel.actualizarClase(clase);
                    Toast.makeText(getContext(), "Nombre actualizado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBorrar(Clase clase) {
                showDeleteConfirmationDialog(clase);
            }
        });

        rv.setAdapter(adapter);

        // Observamos las clases y actualizamos el contador
        viewModel.obtenerClases().observe(getViewLifecycleOwner(), clases -> {
            adapter.setClases(clases);
            cantidadClases = clases.size(); // Guardamos cuántas hay
        });

        // --- LÓGICA DEL BOTÓN AÑADIR MODIFICADA ---
        FloatingActionButton fab = view.findViewById(R.id.irANuevoAlumno);
        fab.setOnClickListener(v -> {
            if (cantidadClases > 0) {
                // Si hay clases, mostramos el diálogo para elegir
                mostrarDialogoEleccion(view);
            } else {
                // Si no hay clases, va directo a crear clase (no se puede crear alumno sin clase)
                Navigation.findNavController(view).navigate(R.id.action_clases_to_nuevaClase);
            }
        });
    }

    /**
     * Muestra un diálogo para elegir qué crear
     */
    private void mostrarDialogoEleccion(View view) {
        String[] opciones = {"Nueva Clase", "Nuevo Alumno"};

        new AlertDialog.Builder(getContext())
                .setTitle("¿Qué quieres añadir?")
                .setItems(opciones, (dialog, which) -> {
                    if (which == 0) {
                        // Opción 0: Nueva Clase
                        Navigation.findNavController(view).navigate(R.id.action_clases_to_nuevaClase);
                    } else {
                        // Opción 1: Nuevo Alumno
                        // Al no pasar argumentos, el FragmentNuevoAlumno mostrará el Spinner desbloqueado
                        Navigation.findNavController(view).navigate(R.id.action_clases_to_nuevoAlumno);
                    }
                })
                .show();
    }

    private void showDeleteConfirmationDialog(Clase clase) {
        new AlertDialog.Builder(getContext())
                .setTitle("Borrar Clase")
                .setMessage("¿Estás seguro de borrar la clase \"" + clase.nombre + "\"?\n\nATENCIÓN: Se borrarán TODOS los alumnos de esta clase.")
                .setPositiveButton("Sí, Borrar todo", (dialog, which) -> {
                    viewModel.eliminarClase(clase);
                    Toast.makeText(getContext(), "Clase eliminada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}