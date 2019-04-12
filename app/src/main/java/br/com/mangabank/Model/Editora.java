package br.com.mangabank.Model;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import java.io.Serializable;


@Entity(tableName = "Editoras",
        indices = {@Index(value = "id", unique = true)})
public class Editora implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String nome;

    public Editora() {
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
