package com.example.ejroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AlumnoViewModel extends AndroidViewModel {
    AlumnosRepositorio alumnosRepositorio;
    public AlumnoViewModel(@NonNull Application application) {
        super(application);
//Inicializo la lista de alumnos
// se le debe pasar la referencia de la aplicación cuando se intancia el listado
                alumnosRepositorio = new AlumnosRepositorio(application);
    }
    public LiveData<List<Alumno>> obtener()
    {
        return alumnosRepositorio.obtener();
    }
    void insertar(Alumno a){
//llama al metodo insertar del modelo y ademas al tener el interfac callback
//necestita que implementemos los metodos de dicho interfaz en este caso notificarcambios
        alumnosRepositorio.insertar(a);
    }
    void eliminar(Alumno a){
        alumnosRepositorio.eliminar(a);
    }
    void actualizar(Alumno elemento, String nombre, float nota){
        alumnosRepositorio.actualizar(elemento, nombre, nota);
    }
}

