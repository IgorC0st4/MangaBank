package br.com.mangabank.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mangabank.Database.AppDatabase;
import br.com.mangabank.Model.*;
import br.com.mangabank.Repository.*;

public class ImportarRegistros extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private ProgressBar progressBar;
    private TextView textView;

    private String path;

    private List<Editora> editoras;
    private List<Titulo> titulos;
    private List<Volume> volumes;

    public ImportarRegistros(Context context, ProgressBar progressBar, TextView textView, String path) {
        this.progressBar = progressBar;
        this.textView = textView;
        this.path = path;
        this.context = context;

        editoras = new ArrayList<>();
        titulos = new ArrayList<>();
        volumes = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        textView.setText("Importando resgistros...");
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        String strEditoras = "";
        String strTitulos = "";
        String strVolumes = "";

        String JSON = IOHelper.lerArquivo(path);

        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(JSON);
            strEditoras = (String) jsonObject.get("editoras");
            strTitulos = (String) jsonObject.get("titulos");
            strVolumes = (String) jsonObject.get("volumes");
        }catch (Exception e){
            Log.e("ERRO JSON", e.getMessage());
            e.printStackTrace();
            return false;
        }
        publishProgress();

        JSONArray jsonArray = null;
        try{
            jsonArray = new JSONArray(strEditoras);
            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = (JSONObject) jsonArray.get(i);
                Editora editora = new Editora();
                editora.setId(jsonObject.getLong("id"));
                editora.setNome(jsonObject.getString("nome"));
                editoras.add(editora);
            }
        }catch (Exception e){
            Log.e("ERRO JSON", e.getMessage());
            e.printStackTrace();
            return false;
        }

        try{
            AppDatabase.getAppDatabase(context).editoraDao().salvarLista(editoras);
        }catch (Exception e){
            Log.e("ERRO SALVAR", e.getMessage());
            e.printStackTrace();
            return false;

        }
        publishProgress();

        try{
            jsonArray = new JSONArray(strTitulos);
            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = (JSONObject) jsonArray.get(i);

                Titulo titulo = new Titulo();
                titulo.setId(jsonObject.getLong("id"));
                titulo.setEstadoDoTitulo(jsonObject.getString("estadoDoTitulo"));
                titulo.setId_editora(jsonObject.getLong("id_editora"));
                titulo.setNome(jsonObject.getString("nome"));
                titulo.setNumTotalDeVolumes(jsonObject.getInt("numTotalDeVolumes"));
                titulo.setTipoDeTitulo(jsonObject.getString("tipoDeTitulo"));

                titulos.add(titulo);
            }
        }catch (Exception e){
            Log.e("ERRO JSON", e.getMessage());
            e.printStackTrace();
            return false;
        }

        try{
            AppDatabase.getAppDatabase(context).tituloDao().salvarLista(titulos);
        }catch (Exception e){
            Log.e("ERRO SALVAR", e.getMessage());
            e.printStackTrace();
            return false;

        }
        publishProgress();

        try{
            jsonArray = new JSONArray(strVolumes);
            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = (JSONObject) jsonArray.get(i);

                Volume volume = new Volume();
                volume.setLido(jsonObject.getBoolean("lido"));
                volume.setId(jsonObject.getLong("id"));
                volume.setId_titulo(jsonObject.getLong("id_titulo"));
                volume.setNomeDoVolume(jsonObject.getString("nomeDoVolume"));
                volume.setNum(jsonObject.getInt("num"));

                volumes.add(volume);
            }
        }catch (Exception e){
            Log.e("ERRO JSON", e.getMessage());
            e.printStackTrace();
            return false;
        }
        try{
            AppDatabase.getAppDatabase(context).volumeDao().salvarLista(volumes);
        }catch (Exception e){
            Log.e("ERRO SALVAR", e.getMessage());
            e.printStackTrace();
            return false;

        }
        publishProgress();

        return true;

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        progressBar.incrementProgressBy(25);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean s) {
        if (s) {
            textView.setText("ARQUIVOS CONVERTIDOS");
        }else {
            textView.setText("TENTE NOVAMENTE");
        }
        super.onPostExecute(s);
    }
}
