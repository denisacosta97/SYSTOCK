package com.isii.systock.Interfaces.Actividades;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.isii.systock.Interfaces.Adaptadores.OpcionesAdapter;
import com.isii.systock.Interfaces.Actividades.GestionClientes.AnadirClienteActivity;
import com.isii.systock.Interfaces.Actividades.GestionClientes.EliminarClienteActivity;
import com.isii.systock.Interfaces.Actividades.GestionClientes.ListadoClientesActivity;
import com.isii.systock.Interfaces.Actividades.GestionClientes.ModificarClienteActivity;
import com.isii.systock.Modelo.Opciones;
import com.isii.systock.R;
import com.isii.systock.Herramientas.RecyclerListener.ItemClickSupport;

import java.util.ArrayList;

public class ClienteOpcionesActivity extends AppCompatActivity {

    //Objetos del Sistema
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecycler;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mList;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_opciones);

        mRecycler = findViewById(R.id.recycler);

        agregarToolbar();

        mList = new ArrayList<>();
        mList.add(new Opciones(1, "Añadir Cliente", R.drawable.ic_agregar_cliente));
        mList.add(new Opciones(2, "Eliminar Cliente", R.drawable.ic_remover_cliente));
        mList.add(new Opciones(3, "Modificar Cliente", R.drawable.ic_editar_cliente));
        mList.add(new Opciones(4, "Listado de Clientes", R.drawable.ic_list));


        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setNestedScrollingEnabled(false);
        mAdapter = new OpcionesAdapter(mList, getApplicationContext());
        mRecycler.setAdapter(mAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecycler);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                onClick(id);
            }
        });


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

    private void onClick(long id) {
        switch ((int)id){
            case 1:
                startActivity(new Intent(getApplicationContext(), AnadirClienteActivity.class));
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(), EliminarClienteActivity.class));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), ModificarClienteActivity.class));
                break;
            case 4:
                startActivity(new Intent(getApplicationContext(), ListadoClientesActivity.class));
                break;
        }
    }
}
