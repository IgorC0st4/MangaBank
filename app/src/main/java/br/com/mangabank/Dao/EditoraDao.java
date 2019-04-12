package br.com.mangabank.Dao;

import android.arch.persistence.room.*;

import java.util.List;

import br.com.mangabank.Model.Editora;

@Dao
public interface EditoraDao {

    @Insert
    void salvar (Editora editora);

    @Update
    void atualizar (Editora editora);

    @Delete
    void deletar (Editora editora);

    @Delete
    void deletarLista (List<Editora> editoras);

    @Query("SELECT * FROM Editoras ORDER BY nome ASC")
    List<Editora> getAll();

    @Query("SELECT * FROM Editoras WHERE nome LIKE :nome ORDER BY nome ASC")
    List<Editora> pesquisar(String nome);
}
