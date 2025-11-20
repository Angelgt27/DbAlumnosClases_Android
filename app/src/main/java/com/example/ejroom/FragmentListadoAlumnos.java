package com.example.ejroom;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentListadoAlumnos extends Fragment {

    private AlumnoViewModel alumnoViewModel;
    private AlumnosAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listado_alumnos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializamos el adapter con la lógica para Modificar y Borrar
        adapter = new AlumnosAdapter(new ArrayList<>(),
                (alumno, nuevoNombre, nuevaNota) -> {
                    // Lógica al pulsar MODIFICAR
                    alumnoViewModel.actualizar(alumno, nuevoNombre, nuevaNota);
                },
                (alumno, x, y) -> {
                    // Lógica al pulsar BORRAR
                    showDeleteConfirmationDialog(getContext(), alumno);
                    //alumnoViewModel.eliminar(alumno);
                }
        );

        recyclerView.setAdapter(adapter);

        alumnoViewModel = new ViewModelProvider(this).get(AlumnoViewModel.class);

        alumnoViewModel.obtener().observe(getViewLifecycleOwner(), alumnos -> {
            adapter.setAlumnos(alumnos);
        });

        FloatingActionButton fab = view.findViewById(R.id.irANuevoAlumno);
        fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.fragmentNuevoAlumno);
        });
    }

    private void showDeleteConfirmationDialog(Context context, Alumno a) {
        new AlertDialog.Builder(context)
                // Título del diálogo
                .setTitle("Confirmar Borrado")
                // Pregunta de comprobación
                .setMessage("¿Está seguro de borrar este alumno: \"" +
                        a.getNombre() + "\"? Esta acción es irreversible.")
                // Botón de confirmación (Positivo)
                .setPositiveButton("Sí, Borrar", (dialog, which) -> {
                    // Si el usuario hace clic en "Sí, Borrar",ejecutamos la acción
                    alumnoViewModel.eliminar(a);
                    Toast.makeText(context, "Alumno '" + a.getNombre() +
                            "' eliminado.", Toast.LENGTH_SHORT).show();
                })
                // Botón de cancelación (Negativo)
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    // Si el usuario hace clic en "Cancelar", simplemente se cierra el diálogo
                    dialog.dismiss();
                })
                .setIcon(android.R.drawable.ic_dialog_alert) // Icono de alerta
                .show();
    }
}