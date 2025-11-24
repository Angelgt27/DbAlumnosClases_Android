package com.example.ejroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AlumnoViewModel extends AndroidViewModel {
    AlumnosRepositorio repositorio;

    public AlumnoViewModel(@NonNull Application application) {
        super(application);
        repositorio = new AlumnosRepositorio(application);
    }

    public LiveData<List<Alumno>> obtenerAlumnos(int claseId) {
        return repositorio.obtenerAlumnosPorClase(claseId);
    }

    public void insertarAlumno(Alumno a) { repositorio.insertarAlumno(a); }

    void eliminar(Alumno a){repositorio.eliminarAlumno(a);}
    void actualizar(Alumno elemento, String nombre, float nota){
        repositorio.actualizarAlumno(elemento, nombre, nota);
    }
    public LiveData<List<Clase>> obtenerClases() { return repositorio.obtenerClases(); }
    public void insertarClase(Clase c) { repositorio.insertarClase(c); }
}


