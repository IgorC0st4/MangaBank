package br.com.mangabank.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import br.com.mangabank.Model.Editora;
import br.com.mangabank.Model.Titulo;
import br.com.mangabank.Model.Volume;
import br.com.mangabank.Repository.EditoraRepository;
import br.com.mangabank.Repository.TituloRepository;
import br.com.mangabank.Repository.VolumeRepository;

public class ExportarRegistros extends AsyncTask<Void, Void, Boolean> {

    private ProgressBar progressBar;
    private TextView textView;

    private EditoraRepository editoraRepository;
    private TituloRepository tituloRepository;
    private VolumeRepository volumeRepository;

    private List<Editora> editoras;
    private List<Titulo> titulos;
    private List<Volume> volumes;

    public ExportarRegistros(Context context, ProgressBar progressBar, TextView textView) {
        this.progressBar = progressBar;
        this.textView = textView;

        editoraRepository = new EditoraRepository(context);
        tituloRepository = new TituloRepository(context);
        volumeRepository = new VolumeRepository(context);

        editoras = editoraRepository.listar();
        titulos = tituloRepository.listar();
        volumes = volumeRepository.listar();
    }

    @Override
    protected void onPreExecute() {
        textView.setText("Exportando registros...");
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        JSONObject jsonObject = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            jsonObject.put("editoras", objectMapper.writeValueAsString(editoras));
            publishProgress();
            jsonObject.put("titulos", objectMapper.writeValueAsString(titulos));
            publishProgress();
            jsonObject.put("volumes", objectMapper.writeValueAsString(volumes));
            publishProgress();
        }catch (Exception e){
            Log.e("ERRO JSON", e.getMessage());
            e.printStackTrace();
            return false;
        }

        publishProgress();

        return IOHelper.escreverArquivo(jsonObject.toString());

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        progressBar.incrementProgressBy(25);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean s) {
        if (s) {
            textView.setText("Concluido!");
        }else {
            textView.setText("Ocorreu um erro! Tente novamente.");
        }
        super.onPostExecute(s);
    }
}
