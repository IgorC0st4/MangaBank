package br.com.mangabank.Interface;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.mangabank.Model.Titulo;
import br.com.mangabank.Model.Volume;
import br.com.mangabank.R;
import br.com.mangabank.Repository.VolumeRepository;
import br.com.mangabank.Util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class VolumesTituloActivity extends AppCompatActivity {
    @BindView(R.id.lbl_total_de_volumes_cadastrados)
    TextView lbl_total;

    @BindView(R.id.fab)
    FloatingActionButton fab_add;

    @BindView(R.id.list_volumes)
    ListView list_volumes;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_volume_pesquisa)
    EditText txt_volume_pesquisa;

    private Titulo titulo;

    private VolumeRepository volumeRepository;
    private List<Volume> volumes;

    static final int SALVAR = Menu.FIRST;
    static final int SALVAR_VARIOS = Menu.FIRST + 1;
    static final int ATUALIZAR = Menu.FIRST + 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volumes_titulo);
        ButterKnife.bind(this);

        try {
            titulo = (Titulo) getIntent().getSerializableExtra("titulo");
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao recuperar dados", Toast.LENGTH_SHORT).show();
            Log.e("ERRO INTENT", e.getMessage());
            e.printStackTrace();
            finish();
        }

        toolbar.setTitle(titulo.getNome());
        setSupportActionBar(toolbar);

        volumeRepository = new VolumeRepository(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        volumes = volumeRepository.listar(titulo.getId());
        populaTela();
    }

    private void populaTela() {
        int qtdeDeVolumesCadastrados = volumeRepository.contarVolumesCadastrados(titulo.getId());

        if (titulo.getNumTotalDeVolumes() == 0)
            lbl_total.setText("Volumes: " + qtdeDeVolumesCadastrados + "/??");
        else
            lbl_total.setText("Volumes: " + qtdeDeVolumesCadastrados + "/" + titulo.getNumTotalDeVolumes());

        list_volumes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<Volume> adapter = new ArrayAdapter<Volume>(this, android.R.layout.simple_list_item_checked, volumes);
        list_volumes.setAdapter(adapter);

        list_volumes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView v = (CheckedTextView) view;
                Volume volume = volumes.get(position);
                volume.setLido(v.isChecked());
                atualizarVolume(volume);
                Log.e("" + volume.getNum(), volume.toStringCompleto());
            }
        });

        list_volumes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                editarVolume(position);
                return false;
            }
        });
        for (int i = 0; i < volumes.size(); i++) {
            list_volumes.setItemChecked(i, volumes.get(i).isLido());
        }

    }

    private void atualizarVolume(Volume volume) {
        if (volumeRepository.atualizar(volume)) {

            volumes = volumeRepository.listar(titulo.getId());
            populaTela();
        } else
            Toast.makeText(VolumesTituloActivity.this, "Houve um erro ao salvar as alterações!", Toast.LENGTH_SHORT).show();

    }

    public void editarVolume(int position) {
        criarDialog("Editar volume", "Nome do volume:", ATUALIZAR, position);
    }

    @OnClick(R.id.fab)
    public void adicionarVolume() {
        criarDialog("Adicionar volume", "Número do volume:", SALVAR, 0);
    }

    @OnTextChanged(R.id.txt_volume_pesquisa)
    public void pesquisa() {
        String volume = txt_volume_pesquisa.getText().toString().trim();
        if (volume.equals("")) {
            volumes = volumeRepository.listar(titulo.getId());
        } else {
            volumes = volumeRepository.pesquisar(titulo.getId(), Integer.parseInt(volume));
        }
        populaTela();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        menu.add(0, SALVAR, 0, "Novo Volume");
        menu.add(0, SALVAR_VARIOS, 0, "Novos Volumes");
        menu.add(0, ATUALIZAR, 0, "Editar");

        return true;
    }

    public void criarDialog(String title, String message, final int acao, final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        if (acao == ATUALIZAR)
            input.setText(volumes.get(position).getNomeDoVolume());
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Salvar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Volume volume = new Volume();
                        switch (acao) {
                            case SALVAR:

                                String strNumVolume = input.getText().toString().trim();

                                if (Utils.isCampoVazio(strNumVolume)) {
                                    Toast.makeText(VolumesTituloActivity.this, "Campo vazio!", Toast.LENGTH_SHORT).show();
                                } else {
                                    volume.setNum(Integer.parseInt(strNumVolume));
                                    volume.setNomeDoVolume(titulo.getNome() + " - " + strNumVolume);
                                    volume.setId_titulo(titulo.getId());
                                    volume.setLido(false);

                                    if (volumeRepository.salvar(volume)) {
                                        Toast.makeText(VolumesTituloActivity.this, "Volume salvo com sucesso!", Toast.LENGTH_SHORT).show();
                                        volumes = volumeRepository.listar(titulo.getId());
                                        populaTela();
                                    } else {
                                        Toast.makeText(VolumesTituloActivity.this, "Houve um erro ao salvar o volume!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                break;
                            case SALVAR_VARIOS:
                                int qtdeSalvar = Integer.parseInt(input.getText().toString());
                                int qtdeMaxima = titulo.getNumTotalDeVolumes();
                                if (((qtdeMaxima != 0) || (qtdeMaxima < qtdeSalvar)) && (volumes.size() + qtdeSalvar > qtdeMaxima)) {
                                    Toast.makeText(VolumesTituloActivity.this, "Quantidade inválida de volumes. Máx: " + qtdeMaxima, Toast.LENGTH_LONG).show();
                                } else {
                                    List<Volume> salvar = new ArrayList<>();
                                    int numVolume = 1;
                                    if (volumes.size() != 0) {
                                        numVolume = volumes.size() + 1;
                                    }
                                    for (int i = 0; i < qtdeSalvar; i++) {
                                        volume = new Volume();
                                        volume.setNum(numVolume);
                                        volume.setNomeDoVolume(titulo.getNome() + " - " + numVolume);
                                        volume.setId_titulo(titulo.getId());
                                        volume.setLido(false);
                                        numVolume++;
                                        salvar.add(volume);
                                    }
                                    if (volumeRepository.salvarLista(salvar)) {
                                        Toast.makeText(VolumesTituloActivity.this, "Volumes salvos com sucesso!", Toast.LENGTH_SHORT).show();
                                        volumes = volumeRepository.listar(titulo.getId());
                                        populaTela();
                                    } else {
                                        Toast.makeText(VolumesTituloActivity.this, "Houve um erro ao salvar os volumes!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                break;
                            case ATUALIZAR:
                                volume = volumes.get(position);
                                String strNome = input.getText().toString();

                                if (Utils.isCampoVazio(strNome)) {
                                    Toast.makeText(VolumesTituloActivity.this, "Campo vazio!", Toast.LENGTH_SHORT).show();
                                } else {
                                    volume.setNomeDoVolume(strNome);
                                    atualizarVolume(volume);
                                }
                                break;
                        }

                    }
                });
        alertDialog.setNegativeButton("Cancelar", null);
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                criarDialog("Adicionar volume", "Número do volume:", SALVAR, 0);
                return true;
            case SALVAR_VARIOS:
                criarDialog("Adicionars volumes", "Quantidade de volumes:", SALVAR_VARIOS, 0);
                return true;
            case ATUALIZAR:
                Toast.makeText(this, "Dê um clique e segure em um item para editar!", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
