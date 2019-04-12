package br.com.mangabank.Interface;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.mangabank.Model.Editora;
import br.com.mangabank.Model.Titulo;
import br.com.mangabank.R;
import br.com.mangabank.Repository.TituloRepository;
import br.com.mangabank.Util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrudTituloActivity extends AppCompatActivity {
    @BindView(R.id.txt_editora)
    EditText txt_editora;

    @BindView(R.id.txt_nome_titulo)
    EditText txt_nome;

    @BindView(R.id.txt_total_de_volumes)
    EditText txt_total_volumes;

    @BindView(R.id.spin_tipo_de_titulo)
    Spinner spin_tipo;

    @BindView(R.id.spin_estado_do_titulo)
    Spinner spin_estado;

    private Titulo titulo;

    private Editora editora;

    private TituloRepository repository;

    String[] tipos;
    String[] estados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_titulo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.lbl_caracteristicas);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        tipos = getResources().getStringArray(R.array.tipos_de_titulo);
        estados = getResources().getStringArray(R.array.estados_do_titulo);

        try {
            repository = new TituloRepository(this);
            editora = (Editora) getIntent().getSerializableExtra("editora");
            titulo = (Titulo) getIntent().getSerializableExtra("titulo");
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao recuperar dados", Toast.LENGTH_SHORT).show();
            Log.e("ERRO INTENT", e.getMessage());
            e.printStackTrace();
            finish();
        }

        txt_editora.setText(editora.getNome());

        if (titulo != null) {
            populaTela();
        } else {
            titulo = new Titulo();
        }
    }

    private void populaTela() {
        txt_nome.setText(titulo.getNome());
        txt_total_volumes.setText("" + titulo.getNumTotalDeVolumes());

        for (int i = 0; i < tipos.length; i++) {
            if (tipos[i].equals(titulo.getTipoDeTitulo())) {
                spin_tipo.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < estados.length; i++) {
            if (estados[i].equals(titulo.getEstadoDoTitulo())) {
                spin_estado.setSelection(i);
                break;
            }
        }
    }

    @OnClick(R.id.fab)
    public void salvarTitulo() {


        String nome = txt_nome.getText().toString().trim();
        String strTotalVolumes = txt_total_volumes.getText().toString().trim();

        int totalVolumes;
        if (Utils.isCampoVazio(strTotalVolumes)) {
            totalVolumes = 0;
        } else {
            totalVolumes = Integer.parseInt(strTotalVolumes);
        }

        String tipo = tipos[spin_tipo.getSelectedItemPosition()];
        String estado = estados[spin_estado.getSelectedItemPosition()];


        if (Utils.isCampoVazio(nome)) {
            txt_nome.requestFocus();
            Toast.makeText(this, "Campo nome vazio", Toast.LENGTH_SHORT).show();
        } else {
            if (titulo.getId() == 0) {
                titulo = new Titulo(editora.getId(), nome, tipo, estado, totalVolumes);

                if (repository.salvar(titulo)) {
                    Toast.makeText(this, "Título salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Houve um erro ao salvar o título!", Toast.LENGTH_SHORT).show();
                }
            } else {
                titulo.setNumTotalDeVolumes(totalVolumes);
                titulo.setNome(nome);
                titulo.setEstadoDoTitulo(estado);
                titulo.setTipoDeTitulo(tipo);

                if (repository.atualizar(titulo)) {
                    Toast.makeText(this, "Título atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Houve um erro ao salvar o título!", Toast.LENGTH_SHORT).show();
                }
            }


        }

    }
}
