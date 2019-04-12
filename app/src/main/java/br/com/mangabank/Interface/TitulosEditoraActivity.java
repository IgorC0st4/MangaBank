package br.com.mangabank.Interface;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.mangabank.Model.Editora;
import br.com.mangabank.Model.Titulo;
import br.com.mangabank.R;
import br.com.mangabank.Repository.TituloRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnTextChanged;

public class TitulosEditoraActivity extends AppCompatActivity {

    @BindView(R.id.list_titulos)
    ListView list_titulos;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_nome_pesquisa)
    EditText txt_nome_pesquisa;

    private TituloRepository tituloRepository;
    private List<Titulo> titulos;

    private Editora editora;

    static final int SALVAR = Menu.FIRST;
    static final int ATUALIZAR = Menu.FIRST + 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titulos_editora);
        ButterKnife.bind(this);

        try {
            editora = (Editora) getIntent().getSerializableExtra("editora");
        }catch (Exception e) {
            Toast.makeText(this, "Houve um erro ao recuperar os dados", Toast.LENGTH_SHORT).show();
            Log.e("ERRO INTENT", e.getMessage());
            e.printStackTrace();
            finish();
        }

        toolbar.setTitle(editora.getNome());
        setSupportActionBar(toolbar);

        tituloRepository = new TituloRepository(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        titulos = tituloRepository.listar(editora.getId());
        populaTela();
    }


    private void populaTela() {
        ArrayAdapter<Titulo> adapter = new ArrayAdapter<Titulo>(this, android.R.layout.simple_list_item_1,titulos);
        list_titulos.setAdapter(adapter);
    }

    @OnItemClick(R.id.list_titulos)
    public void visualizarVolumes(int position) {
        Titulo titulo = titulos.get(position);
        Intent intent = new Intent(this, VolumesTituloActivity.class);
        intent.putExtra("titulo", titulo);
        startActivity(intent);
    }

    @OnItemLongClick(R.id.list_titulos)
    public boolean editarTitulo(int position) {
        Titulo titulo = titulos.get(position);

        Intent intent = new Intent(this, CrudTituloActivity.class);
        intent.putExtra("editora", editora);
        intent.putExtra("titulo", titulo);
        startActivity(intent);

        return true;
    }

    @OnClick(R.id.fab)
    public void adicionarTitulo() {
        Intent intent = new Intent(this, CrudTituloActivity.class);
        intent.putExtra("editora", editora);
        startActivity(intent);
    }

    @OnTextChanged(R.id.txt_nome_pesquisa)
    public void pesquisa() {
        String nome = txt_nome_pesquisa.getText().toString().trim();
        if (nome.equals("")) {
            titulos = tituloRepository.listar(editora.getId());
        } else {
            titulos = tituloRepository.pesquisar(editora.getId(), nome);
        }
        populaTela();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        menu.add(0, SALVAR, 0, "Novo Título");
        menu.add(0, ATUALIZAR, 0, "Editar");

        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case SALVAR:
                adicionarTitulo();
                return true;
            case ATUALIZAR:
                Toast.makeText(this, "Dê um clique longo em um item para editar!", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
