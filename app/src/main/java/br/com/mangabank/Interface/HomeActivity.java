package br.com.mangabank.Interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.mangabank.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.btn_editoras)
    public void telaEditoras(){
        Intent intent = new Intent(this, EditorasActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_todos)
    public void telaTodosOsTitulos(){
        Intent intent = new Intent(this, TodosOsTitulosActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_ultimos)
    public void telaUltimosAdicionados(){
        Intent intent = new Intent(this, UltimosAdicionadosActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnExportarImportar)
    public void telaExportarImportar(){
        Intent intent = new Intent(this, ConverterActivity.class);
        startActivity(intent);
    }
}
