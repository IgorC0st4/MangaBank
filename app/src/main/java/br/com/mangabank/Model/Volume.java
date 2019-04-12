package br.com.mangabank.Model;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import java.io.Serializable;


@Entity(tableName = "Volumes",
        foreignKeys = {@ForeignKey(entity = Titulo.class,
                parentColumns = "id",
                childColumns = "id_titulo")},
        indices = {@Index(value = {"id"}, unique = true),
                @Index(value = {"id_titulo"})})
public class Volume implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private int num;

    @NonNull
    @ColumnInfo(name = "id_titulo")
    private long id_titulo;

    @NonNull
    private String nomeDoVolume;

    @NonNull
    private boolean isLido;

    public Volume() {
    }

    @Ignore
    public Volume(int num, long id_titulo, @NonNull String nomeDoVolume, boolean isLido) {
        this.num = num;
        this.id_titulo = id_titulo;
        this.nomeDoVolume = nomeDoVolume;
        this.isLido = isLido;
    }

    public boolean isLido() {
        return isLido;
    }

    public void setLido(boolean lido) {
        isLido = lido;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    @NonNull
    public int getNum() {
        return num;
    }

    public void setNum(@NonNull int num) {
        this.num = num;
    }

    @NonNull
    public long getId_titulo() {
        return id_titulo;
    }

    public void setId_titulo(@NonNull long id_titulo) {
        this.id_titulo = id_titulo;
    }

    @NonNull
    public String getNomeDoVolume() {
        return nomeDoVolume;
    }

    public void setNomeDoVolume(@NonNull String nomeDoVolume) {
        this.nomeDoVolume = nomeDoVolume;
    }

    @Override
    public String toString() {
        return nomeDoVolume;
    }
}
