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

public class FragmentListadoClases extends Fragment {
    private AlumnoViewModel viewModel;
    private ClaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listado_alumnos, container, false); // Reutilizo el layout frame/recycler/fab
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Al hacer click en una clase, vamos a los alumnos de esa clase
        adapter = new ClaseAdapter(new ArrayList<>(), clase -> {
            Bundle bundle = new Bundle();
            bundle.putInt("claseId", clase.getId());
            Navigation.findNavController(view).navigate(R.id.action_clases_to_alumnos, bundle);
        });
        rv.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AlumnoViewModel.class);
        viewModel.obtenerClases().observe(getViewLifecycleOwner(), clases -> adapter.setClases(clases));

        FloatingActionButton fab = view.findViewById(R.id.irANuevoAlumno);
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_clases_to_nuevaClase));
    }
}