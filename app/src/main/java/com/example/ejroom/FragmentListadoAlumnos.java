package com.example.ejroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class FragmentListadoAlumnos extends Fragment {
    private AlumnoViewModel viewModel;
    private AlumnosAdapter adapter;
    private int claseId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            claseId = getArguments().getInt("claseId");
        }
    }

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

        adapter = new AlumnosAdapter(new ArrayList<>(),
                (alumno, nom, nota) -> {
                    alumno.setNombre(nom);
                    alumno.setNota(nota);
                    viewModel.actualizarAlumno(alumno,nom,nota);
                },
                (alumno, x, y) -> {
                    showDeleteConfirmationDialog(getContext(), alumno);
                }
        );

        rv.setAdapter(adapter);

        // Observamos SOLO los alumnos de esta clase
        viewModel.obtenerAlumnos(claseId).observe(getViewLifecycleOwner(), alumnos -> adapter.setAlumnos(alumnos));

        FloatingActionButton fab = view.findViewById(R.id.irANuevoAlumno);
        fab.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("claseId", claseId); // Pasamos el ID para preseleccionar
            Navigation.findNavController(view).navigate(R.id.action_alumnos_to_nuevoAlumno, bundle);
        });
    }

    private void showDeleteConfirmationDialog(android.content.Context context, Alumno a) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                // Título del diálogo
                .setTitle("Confirmar Borrado")
                // Pregunta de comprobación
                .setMessage("¿Está seguro de borrar este alumno: \"" + a.getNombre() + "\"? Esta acción es irreversible.")
                // Botón de confirmación (Positivo)
                .setPositiveButton("Sí, Borrar", (dialog, which) -> {
                    // Si el usuario hace clic en "Sí, Borrar", ejecutamos la acción
                    viewModel.eliminarAlumno(a);
                    android.widget.Toast.makeText(context, "Alumno '" + a.getNombre() + "' eliminado.", android.widget.Toast.LENGTH_SHORT).show();
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