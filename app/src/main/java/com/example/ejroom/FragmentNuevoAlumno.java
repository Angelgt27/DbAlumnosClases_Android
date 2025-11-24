package com.example.ejroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import java.util.List;

public class FragmentNuevoAlumno extends Fragment {
    private AlumnoViewModel viewModel;
    private Spinner spinner;
    private int clasePreseleccionadaId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clasePreseleccionadaId = getArguments().getInt("claseId", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nuevo_alumno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AlumnoViewModel.class);

        spinner = view.findViewById(R.id.spinner_clases);
        EditText etNombre = view.findViewById(R.id.texto_nombre);
        EditText etNota = view.findViewById(R.id.texto_nota);
        Button btnGuardar = view.findViewById(R.id.boton_guardar);

        // Llenar Spinner
        viewModel.obtenerClases().observe(getViewLifecycleOwner(), clases -> {
            ArrayAdapter<Clase> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, clases);
            spinner.setAdapter(adapter);

            // Si venimos de una clase concreta, la seleccionamos
            if (clasePreseleccionadaId != -1) {
                for (int i = 0; i < clases.size(); i++) {
                    if (clases.get(i).getId() == clasePreseleccionadaId) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        });

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String notaStr = etNota.getText().toString();
            Clase claseSeleccionada = (Clase) spinner.getSelectedItem();

            if (!nombre.isEmpty() && !notaStr.isEmpty() && claseSeleccionada != null) {
                viewModel.insertarAlumno(new Alumno(nombre, Float.parseFloat(notaStr), claseSeleccionada.getId()));
                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(getContext(), "Faltan datos o no hay clases", Toast.LENGTH_SHORT).show();
            }
        });
    }
}