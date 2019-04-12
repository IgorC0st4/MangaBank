package br.com.mangabank.Dao;

import android.arch.persistence.room.*;

import java.util.List;

import br.com.mangabank.Model.Volume;

@Dao
public interface VolumeDao {
    @Insert
    void salvar (Volume volume);

    @Update
    void atualizar (Volume volume);

    @Delete
    void deletar (Volume volume);

    @Delete
    void deletarLista (List<Volume> volumes);

    @Query("SELECT * FROM Volumes WHERE id_titulo = :id_titulo ORDER BY num ASC")
    List<Volume> getAllFromTitulo(long id_titulo);

    @Query("SELECT COUNT(*) FROM Volumes WHERE id_titulo = :id_titulo")
    int countNumVolumesFromTitulo(long id_titulo);

    @Query("SELECT * FROM Volumes ORDER BY id DESC LIMIT 20")
    List<Volume> getUltimos();

    @Query("SELECT * FROM Volumes WHERE id_titulo = :id_editora AND num LIKE :num")
    List<Volume> pesquisar(int num, long id_editora);
}
