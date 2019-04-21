package br.com.mangabank.Dao;

import android.arch.persistence.room.*;

import java.util.List;

import br.com.mangabank.Model.Titulo;

@Dao
public interface TituloDao {
    @Insert
    void salvar (Titulo titulo);

    @Insert
    void salvarLista (List<Titulo> titulos);

    @Update
    void atualizar (Titulo titulo);

    @Delete
    void deletar (Titulo titulo);

    @Delete
    void deletarLista (List<Titulo> titulos);

    @Query("SELECT * FROM Titulos ORDER BY nome ASC")
    List<Titulo> getAll();

    @Query("SELECT * FROM Titulos WHERE nome LIKE :nome ORDER BY nome ASC")
    List<Titulo> pesquisarTodos(String nome);

    @Query("SELECT * FROM Titulos WHERE id_editora = :id_editora ORDER BY nome ASC")
    List<Titulo> getAllFromEditora(long id_editora);

    @Query("SELECT * FROM Titulos WHERE id_editora = :id_editora AND nome LIKE :nome ORDER BY nome ASC")
    List<Titulo> pesquisar(String nome, long id_editora);
}
