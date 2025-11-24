package com.example.ejroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlumnoDao {
    // Método modificado para filtrar por clase
    @Query("SELECT * FROM Alumno WHERE claseId = :claseId")
    LiveData<List<Alumno>> getAlumnosPorClase(int claseId);

    // Resto de métodos iguales...
    @Insert
    void addAlumno(Alumno alumno);
    @Delete
    void deleteAlumno(Alumno alumno);
    @Update
    void updateAlumno(Alumno alumno);
}