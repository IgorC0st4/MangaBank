package br.com.mangabank.Interface;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.mangabank.R;
import br.com.mangabank.Util.ConverterArquivos;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConverterActivity extends AppCompatActivity {
    private final int PERMISSAO_REQUEST = 1;
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

    @OnClick(R.id.btnExportar)
    public void exportar() {
        String JSON = "";
        try{
            JSON = new ConverterArquivos(ConverterActivity.this, progressBar, txtMensagem).execute().get();
        }catch (Exception e){
            Log.e("ERRO CONVERSAO",e.getMessage());
            e.printStackTrace();
        }

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "costa.igor0108@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "LISTA MANGÁS");
        email.putExtra(Intent.EXTRA_TEXT, JSON);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "E-mail"));

    }


    @OnClick(R.id.btnImportar)
    public void importar() {
    }

}
