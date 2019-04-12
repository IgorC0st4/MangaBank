package br.com.mangabank.Database;

import android.arch.persistence.room.*;
import android.content.Context;

import br.com.mangabank.Dao.*;
import br.com.mangabank.Model.*;

@Database(entities = {Editora.class, Titulo.class, Volume.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EditoraDao editoraDao();
    public abstract TituloDao tituloDao();
    public abstract VolumeDao volumeDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mangaApp")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
