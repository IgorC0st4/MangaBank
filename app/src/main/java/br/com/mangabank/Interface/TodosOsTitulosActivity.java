package br.com.mangabank.Interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import br.com.mangabank.Model.Titulo;
import br.com.mangabank.R;
import br.com.mangabank.Repository.TituloRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class TodosOsTitulosActivity extends AppCompatActivity {

    @BindView(R.id.list_titulos)
    ListView list_titulos;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_nome_pesquisa)
    EditText txt_nome_pesquisa;

    private TituloRepository tituloRepository;
    private List<Titulo> titulos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_os_titulos);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.lbl_btn_todos);
        setSupportActionBar(toolbar);

        tituloRepository = new TituloRepository(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        titulos = tituloRepository.listar();
        populaTela();
    }


    private void populaTela() {
        ArrayAdapter<Titulo> adapter = new ArrayAdapter<Titulo>(this, android.R.layout.simple_list_item_1, titulos);
        list_titulos.setAdapter(adapter);
    }

    @OnItemClick(R.id.list_titulos)
    public void visualizarVolumes(int position) {
        Titulo titulo = titulos.get(position);
        Intent intent = new Intent(this, VolumesTituloActivity.class);
        intent.putExtra("titulo", titulo);
        startActivity(intent);
    }

    @OnTextChanged(R.id.txt_nome_pesquisa)
    public void pesquisa() {
        String nome = txt_nome_pesquisa.getText().toString().trim();
        if (nome.equals("")) {
            titulos = tituloRepository.listar();
        } else {
            titulos = tituloRepository.pesquisar(nome);
        }
        populaTela();
    }
}
