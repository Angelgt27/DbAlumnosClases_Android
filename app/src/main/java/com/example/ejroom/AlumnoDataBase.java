package com.example.ejroom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Alumno.class, Clase.class}, version = 2)
public abstract class AlumnoDataBase extends RoomDatabase
{
    private static volatile AlumnoDataBase INSTANCIA;
    public abstract AlumnoDao getAlumnoDao();
    public abstract ClaseDao getClaseDao();
    public static AlumnoDataBase obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (AlumnoDataBase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context,
                                    AlumnoDataBase.class,
                                    "alumnos.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCIA;
    }
}
