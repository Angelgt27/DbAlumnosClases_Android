package com.example.ejroom;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AlumnosRepositorio {
    AlumnoDao alumnoDao;
    ClaseDao claseDao; // Nuevo
    Executor executor = Executors.newSingleThreadExecutor();

    public AlumnosRepositorio(Application application) {
        AlumnoDataBase db = AlumnoDataBase.obtenerInstancia(application);
        alumnoDao = db.getAlumnoDao();
        claseDao = db.getClaseDao();
    }

    public AlumnosRepositorio(){
    }

    public LiveData<List<Alumno>> obtenerAlumnosPorClase(int claseId) {
        return alumnoDao.getAlumnosPorClase(claseId);
    }

    void insertarAlumno(Alumno alumno){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                alumnoDao.addAlumno(alumno);
            }
        });
    }
    void eliminarAlumno(Alumno alumno) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                alumnoDao.deleteAlumno(alumno);
            }
        });
    }
    public void actualizarAlumno(Alumno a, String nombre, float nota) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                a.setNombre(nombre);
                a.setNota(nota);
                alumnoDao.updateAlumno(a);
            }
        });
    }

    public LiveData<List<Clase>> obtenerClases() {
        return claseDao.obtenerClases();
    }

    public void insertarClase(Clase clase) {
        executor.execute(() -> claseDao.insertarClase(clase));
    }
}