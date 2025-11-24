package com.example.ejroom;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clase")
public class Clase {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public String nombre;

    public Clase(String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre; // Esto es clave para que el Spinner muestre el nombre
    }

    public int getId() { return id; }
}