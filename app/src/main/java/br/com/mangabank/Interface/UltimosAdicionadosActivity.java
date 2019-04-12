package br.com.mangabank.Interface;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.mangabank.Model.Volume;
import br.com.mangabank.R;
import br.com.mangabank.Repository.VolumeRepository;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UltimosAdicionadosActivity extends AppCompatActivity {
    @BindView(R.id.list_volumes)
    ListView list_volumes;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Volume> volumes = new ArrayList<>();
    private VolumeRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimos_adicionados);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.lbl_btn_ultimos);
        setSupportActionBar(toolbar);

        repository = new VolumeRepository(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        volumes = repository.listarUltimos();
        populaTela();
    }

    private void populaTela() {
        ArrayAdapter<Volume> adapter = new ArrayAdapter<Volume>(this, android.R.layout.simple_list_item_1, volumes);
        list_volumes.setAdapter(adapter);
    }

}
