package com.example.ejroom;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentNuevoAlumno extends Fragment {

    private AlumnoViewModel alumnoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nuevo_alumno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alumnoViewModel = new ViewModelProvider(this).get(AlumnoViewModel.class);

        EditText etNombre = view.findViewById(R.id.texto_nombre);
        EditText etNota = view.findViewById(R.id.texto_nota);
        Button btnGuardar = view.findViewById(R.id.boton_guardar);
        Button btnCancelar = view.findViewById(R.id.boton_cancelar);

        // Lógica Botón Guardar
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String notaStr = etNota.getText().toString();

            if (!nombre.isEmpty() && !notaStr.isEmpty()) {
                float nota = Float.parseFloat(notaStr);
                Alumno nuevoAlumno = new Alumno(nombre, nota);

                // Insertar en BD
                alumnoViewModel.insertar(nuevoAlumno);

                // Volver atrás (al listado)
                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(getContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        // Lógica Botón Cancelar
        btnCancelar.setOnClickListener(v -> {
            // Simplemente volvemos atrás sin guardar
            Navigation.findNavController(view).popBackStack();
        });
    }
}