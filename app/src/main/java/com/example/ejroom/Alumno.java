package com.example.ejroom;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Vincula Alumno con Clase. Si se borra la clase, se borran sus alumnos (CASCADE)
@Entity(tableName = "alumno",
        foreignKeys = @ForeignKey(entity = Clase.class,
                parentColumns = "id",
                childColumns = "claseId",
                onDelete = ForeignKey.CASCADE))
public class Alumno {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    @ColumnInfo(name = "nombre")
    String nombre;

    float nota;

    @ColumnInfo(name = "claseId")
    public int claseId;

    public Alumno(String nombre, float nota, int claseId) {
        this.nombre = nombre;
        this.nota = nota;
        this.claseId = claseId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public float getNota() { return nota; }
    public void setNota(float nota) { this.nota = nota; }
    public int getClaseId() { return claseId; }
    public void setClaseId(int claseId) { this.claseId = claseId; }
}