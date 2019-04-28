package com.isii.systock.Interfaces.Actividades.GestionClientes;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.isii.systock.Interfaces.Adaptadores.ClientesAdapter;
import com.isii.systock.Interfaces.Dialogos.VolleyResponse;
import com.isii.systock.Modelo.Cliente;
import com.isii.systock.R;

import java.util.ArrayList;
import java.util.Collection;

public class ListadoClientesActivity extends AppCompatActivity implements VolleyResponse {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecycler;
    ClientesAdapter mAdapter;
    ArrayList<Cliente> list;
    Toolbar mToolbar;
    ProgressBar mProgressBar;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_clientes);

        agregarToolbar();

        mRecycler = findViewById(R.id.recycler);
        mProgressBar = findViewById(R.id.progress);
        mProgressBar.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
        list = new ArrayList<>();

        cliente = new Cliente();
        cliente.setVolleyResponse(this);
        cliente.setContext(getApplicationContext());

        cliente.listadoClientes();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setNestedScrollingEnabled(false);
        mAdapter = new ClientesAdapter(list, getApplicationContext());
        mRecycler.setAdapter(mAdapter);
    }

    private void agregarToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onExisto(Object object) {
        if (object instanceof ArrayList){
            mProgressBar.setVisibility(View.GONE);
            mRecycler.setVisibility(View.VISIBLE);
            list.addAll((Collection<? extends Cliente>) object);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onFallo(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);

    }
}
