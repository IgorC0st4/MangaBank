package br.com.mangabank.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.mangabank.Database.AppDatabase;
import br.com.mangabank.Model.Editora;

public class EditoraRepository {

    private final Context context;

    public EditoraRepository(Context context) {
        this.context = context;
    }

    public boolean salvar(Editora editora) {
        try {
            return new Salvar().execute(editora).get();
        } catch (Exception e) {
            Log.e("ERRO REPO EDITORA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(Editora editora) {
        try {
            return new Deletar().execute(editora).get();
        } catch (Exception e) {
            Log.e("ERRO REPO EDITORA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarLista(List<Editora> editoras) {
        try {
            return new DeletarLista().execute(editoras).get();
        } catch (Exception e) {
            Log.e("ERRO REPO EDITORA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Editora editora) {
        try {
            return new Atualizar().execute(editora).get();
        } catch (Exception e) {
            Log.e("ERRO REPO EDITORA", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Editora> pesquisar(String nome) {
        try {
            String nome_pesquisa = "%" + nome + "%";
            return new Pesquisar().execute(nome_pesquisa).get();
        } catch (Exception e) {
            Log.e("ERRO REPO EDITORA", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Editora> listar() {
        try {
            return new ListarTodos().execute().get();
        } catch (Exception e) {
            Log.e("ERRO REPO EDITORA", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private class Salvar extends AsyncTask<Editora, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Editora... editoras) {
            try {
                AppDatabase.getAppDatabase(context).editoraDao().salvar(editoras[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ASYNC EDITORA", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Deletar extends AsyncTask<Editora, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Editora... editoras) {
            try {
                AppDatabase.getAppDatabase(context).editoraDao().deletar(editoras[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ASYNC EDITORA", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class DeletarLista extends AsyncTask<List<Editora>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<Editora>... lists) {
            List<Editora> editoras = lists[0];
            try {
                AppDatabase.getAppDatabase(context).editoraDao().deletarLista(editoras);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ASYNC EDITORA", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Atualizar extends AsyncTask<Editora, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Editora... editoras) {
            try {
                AppDatabase.getAppDatabase(context).editoraDao().atualizar(editoras[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ASYNC EDITORA", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Pesquisar extends AsyncTask<String, Void, List<Editora>> {

        @Override
        protected List<Editora> doInBackground(String... strings) {
            try {
                return AppDatabase.getAppDatabase(context).editoraDao().pesquisar(strings[0]);
            } catch (Exception e) {
                Log.e("ERRO ASYNC EDITORA", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }


    private class ListarTodos extends AsyncTask<Void, Void, List<Editora>> {

        @Override
        protected List<Editora> doInBackground(Void... voids) {
            try {
                return AppDatabase.getAppDatabase(context).editoraDao().getAll();
            } catch (Exception e) {
                Log.e("ERRO ASYNC EDITORA", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
}
