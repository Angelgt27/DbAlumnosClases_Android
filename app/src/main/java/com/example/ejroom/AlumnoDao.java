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
    //Para devolver la lista de alumno se utiliza LIVEDATA, De esta forma el Array se actualiza cuando cambien los datos
    // de la tabla Alumno y podemos observarlo desde la vista. Por lo cual se observa la BD y ella misma notifica los cambios
    @Query("SELECT * FROM Alumno")
    LiveData<List<Alumno>> getAlumnos();
    @Query("SELECT * FROM alumno WHERE Id LIKE :uuid")
    Alumno getAlumno(String uuid);
    @Insert
    void addAlumno(Alumno alumno);
    @Delete
    void deleteAlumno(Alumno alumno);
    @Update
    void updateAlumno(Alumno alumno);
}