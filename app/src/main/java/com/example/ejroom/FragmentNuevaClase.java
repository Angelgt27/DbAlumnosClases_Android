package com.example.ejroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

public class FragmentNuevaClase extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nueva_clase, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlumnoViewModel vm = new ViewModelProvider(this).get(AlumnoViewModel.class);
        EditText etNombre = view.findViewById(R.id.etNombreNuevaClase);
        Button btn = view.findViewById(R.id.btnGuardarClase);

        btn.setOnClickListener(v -> {
            if (!etNombre.getText().toString().isEmpty()) {
                vm.insertarClase(new Clase(etNombre.getText().toString()));
                Navigation.findNavController(view).popBackStack();
            }
        });
    }
}