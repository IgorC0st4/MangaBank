package br.com.mangabank.Model;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import java.io.Serializable;


@Entity(tableName = "Titulos",
        foreignKeys = {@ForeignKey( entity = Editora.class,
                                    parentColumns = "id",
                                    childColumns = "id_editora",
                                    onDelete = ForeignKey.CASCADE,
                                    onUpdate = ForeignKey.CASCADE)},
        indices = { @Index(value = {"id"}, unique = true),
                    @Index(value = {"id_editora"})})
public class Titulo implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "id_editora")
    private long id_editora;

    @NonNull
    private String nome;

    @NonNull
    private String tipoDeTitulo;

    @NonNull
    private String estadoDoTitulo;

    private int numTotalDeVolumes;

    public Titulo() {
    }

    @Ignore
    public Titulo(long id_editora, @NonNull String nome, @NonNull String tipoDeTitulo, @NonNull String estadoDoTitulo, int numTotalDeVolumes) {
        this.id_editora = id_editora;
        this.nome = nome;
        this.tipoDeTitulo = tipoDeTitulo;
        this.estadoDoTitulo = estadoDoTitulo;
        this.numTotalDeVolumes = numTotalDeVolumes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_editora() {
        return id_editora;
    }

    public void setId_editora(long id_editora) {
        this.id_editora = id_editora;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @NonNull
    public String getTipoDeTitulo() {
        return tipoDeTitulo;
    }

    public void setTipoDeTitulo(@NonNull String tipoDeTitulo) {
        this.tipoDeTitulo = tipoDeTitulo;
    }

    @NonNull
    public String getEstadoDoTitulo() {
        return estadoDoTitulo;
    }

    public void setEstadoDoTitulo(@NonNull String estadoDoTitulo) {
        this.estadoDoTitulo = estadoDoTitulo;
    }

    public int getNumTotalDeVolumes() {
        return numTotalDeVolumes;
    }

    public void setNumTotalDeVolumes(int numTotalDeVolumes) {
        this.numTotalDeVolumes = numTotalDeVolumes;
    }

    @Override
    public String toString() {
        return nome;
    }
}
