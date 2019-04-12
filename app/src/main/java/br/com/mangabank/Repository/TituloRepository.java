package br.com.mangabank.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.mangabank.Database.AppDatabase;
import br.com.mangabank.Model.Titulo;

public class TituloRepository {
    private final Context context;

    public TituloRepository(Context context) {
        this.context = context;
    }

    public boolean salvar(Titulo titulo) {
        try {
            return new Salvar().execute(titulo).get();
        } catch (Exception e) {
            Log.e("ERRO REPO TITULO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(Titulo titulo) {
        try {
            return new Deletar().execute(titulo).get();
        } catch (Exception e) {
            Log.e("ERRO REPO TITULO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarLista(List<Titulo> titulos) {
        try {
            return new DeletarLista().execute(titulos).get();
        } catch (Exception e) {
            Log.e("ERRO REPO TITULO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Titulo titulo) {
        try {
            return new Atualizar().execute(titulo).get();
        } catch (Exception e) {
            Log.e("ERRO REPO TITULO", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Titulo> pesquisar(long id_editora, String nome) {
        try {
            nome = "%" + nome + "%";
            return new Pesquisar(id_editora).execute(nome).get();
        } catch (Exception e) {
            Log.e("ERRO REPO TITULO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Titulo> pesquisar(String nome) {
        try {
            nome = "%" + nome + "%";
            return new Pesquisar(0).execute(nome).get();
        } catch (Exception e) {
            Log.e("ERRO REPO TITULO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Titulo> listar(long id_editora) {
        try {
            return new ListarTodos(id_editora).execute().get();
        } catch (Exception e) {
            Log.e("ERRO REPO TITULO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Titulo> listar() {
        try {
            return new ListarTodos(0).execute().get();
        } catch (Exception e) {
            Log.e("ERRO REPO TITULO", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private class Salvar extends AsyncTask<Titulo, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Titulo... titulos) {
            try {
                AppDatabase.getAppDatabase(context).tituloDao().salvar(titulos[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ASYNC TITULO", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Deletar extends AsyncTask<Titulo, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Titulo... titulos) {
            try {
                AppDatabase.getAppDatabase(context).tituloDao().deletar(titulos[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ASYNC TITULO", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class DeletarLista extends AsyncTask<List<Titulo>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<Titulo>... lists) {
            List<Titulo> titulos = lists[0];
            try {
                AppDatabase.getAppDatabase(context).tituloDao().deletarLista(titulos);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ASYNC TITULO", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Atualizar extends AsyncTask<Titulo, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Titulo... titulos) {
            try {
                AppDatabase.getAppDatabase(context).tituloDao().atualizar(titulos[0]);
                return true;
            } catch (Exception e) {
                Log.e("ERRO ASYNC TITULO", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private class Pesquisar extends AsyncTask<String, Void, List<Titulo>> {
        long id_editora;

        public Pesquisar(long id_editora) {
            this.id_editora = id_editora;
        }

        @Override
        protected List<Titulo> doInBackground(String... strings) {
            try {
                if (id_editora == 0) {
                    return AppDatabase.getAppDatabase(context).tituloDao().pesquisarTodos(strings[0]);
                } else {
                    return AppDatabase.getAppDatabase(context).tituloDao().pesquisar(strings[0], id_editora);
                }
            } catch (Exception e) {
                Log.e("ERRO ASYNC TITULO", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }


    private class ListarTodos extends AsyncTask<Void, Void, List<Titulo>> {
        long id;

        public ListarTodos(long id) {
            this.id = id;
        }

        @Override
        protected List<Titulo> doInBackground(Void... voids) {
            try {
                if (id == 0) {
                    return AppDatabase.getAppDatabase(context).tituloDao().getAll();
                } else {
                    return AppDatabase.getAppDatabase(context).tituloDao().getAllFromEditora(id);
                }
            } catch (Exception e) {
                Log.e("ERRO ASYNC TITULO", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
}
