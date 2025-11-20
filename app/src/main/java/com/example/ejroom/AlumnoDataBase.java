package com.example.ejroom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Alumno.class}, version = 1)
public abstract class AlumnoDataBase extends RoomDatabase
{
    private static volatile AlumnoDataBase INSTANCIA;
    public abstract AlumnoDao getAlumnoDao();
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
