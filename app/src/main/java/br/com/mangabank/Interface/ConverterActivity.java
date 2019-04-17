package br.com.mangabank.Interface;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.mangabank.R;
import br.com.mangabank.Util.ExportarRegistros;
import br.com.mangabank.Util.ImportarRegistros;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConverterActivity extends AppCompatActivity {

    final static int READ_REQUEST_CODE = 42;
    final static int PERMISSION_CODE = 1000;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.txtMensagem)
    TextView txtMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.lbl_exportar_importar);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.btnImportar)
    public void importar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
        escolherArquivo();
    }

    private void escolherArquivo(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(data != null){
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":") + 1);
                boolean resultado = false;
                try{
                    resultado = new ImportarRegistros(this, progressBar, txtMensagem, path).execute().get();
                }catch (Exception e){
                    Log.e("ERRO IMPORTAR", e.getMessage());
                    e.printStackTrace();
                }
                if(resultado){
                    Toast.makeText(this, "Seus registros foram importados com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Hove um erro ao importar seus resgistros! Tente novamente.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @OnClick(R.id.btnExportar)
    public void exportar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }

        boolean resultado = false;
        try{
            resultado = new ExportarRegistros(this, progressBar, txtMensagem).execute().get();
        }catch (Exception e){
            Log.e("ERRO RESULT", e.getMessage());
            e.printStackTrace();
        }
        if(resultado){
            Toast.makeText(this, "Seus arquivos foram exportados com sucesso!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Hove um erro ao exportar seus arquivos! Tente novamente.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Concedido!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Negado!", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


}
