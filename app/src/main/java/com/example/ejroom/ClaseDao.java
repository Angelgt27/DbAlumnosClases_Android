package com.example.ejroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClaseDao {
    @Query("SELECT * FROM Clase")
    LiveData<List<Clase>> obtenerClases();

    @Insert
    void insertarClase(Clase clase);

    @Delete
    void borrar(Clase clase);

    @Update
    void actualizar(Clase clase);
}