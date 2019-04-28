package com.isii.systock.Interfaces.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.isii.systock.Interfaces.Adaptadores.OpcionesAdapter;
import com.isii.systock.Interfaces.Actividades.GestionClientes.EliminarClienteActivity;
import com.isii.systock.Interfaces.Actividades.GestionClientes.ModificarClienteActivity;
import com.isii.systock.Modelo.Opciones;
import com.isii.systock.R;
import com.isii.systock.Herramientas.RecyclerListener.ItemClickSupport;
import com.isii.systock.Herramientas.Utils;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    //Objetos de Android
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecycler;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mList;
    Toolbar mToolbar;
    int mTipoUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mRecycler = findViewById(R.id.recycler);
        mList = new ArrayList<>();

        agregarToolbar();


        if (getIntent().getIntExtra(Utils.KEY_TIPO, -1) != -1) {
            mTipoUser = getIntent().getIntExtra(Utils.KEY_TIPO, -1);
        }

        switch (mTipoUser) {
            case Utils.TYPE_ADMIN:
                mList.add(new Opciones(1, "Gestión de Clientes", R.drawable.ic_clientes));
                mList.add(new Opciones(2, "Gestión de Productos",R.drawable.ic_producto));
                mList.add(new Opciones(3, "Gestión de Proveedores", R.drawable.ic_proveedores));
                mList.add(new Opciones(4, "Gestión de Pedidos",R.drawable.ic_pedidos));
                mList.add(new Opciones(5, "Gestión de Estadisticas",R.drawable.ic_estadistica));
                break;
            case Utils.TYPE_NORMAL:
                mList.add(new Opciones(1, "Realizar Pedido", R.drawable.ic_agregar_pedido));
                mList.add(new Opciones(2, "Ver Pedidos", R.drawable.ic_list_pedido));
                mList.add(new Opciones(3, "Modificar Perfil", R.drawable.ic_editar_cliente));
                mList.add(new Opciones(4, "Eliminar Perfil", R.drawable.ic_remover_cliente));
                break;
        }

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

    }

    private void onClick(long id) {
        switch (mTipoUser) {
            case Utils.TYPE_ADMIN:
                switch ((int) id) {
                    case 1:
                        startActivity(new Intent(getApplicationContext(), ClienteOpcionesActivity.class));
                        break;
                    case 2:
                        //Gestion de Productos
                        startActivity(new Intent(getApplicationContext(), ProductoOpcionesActivity.class));
                        break;
                    case 3:
                        //Gestion de Proveedores
                        break;
                    case 4:
                        //Gestion de Facturas
                        break;
                    case 5:
                        //Gestion de Estadisticas
                        break;
                }
                break;
            case Utils.TYPE_NORMAL:
                switch ((int) id) {
                    case 1:
                        //Realizar pedido
                        break;
                    case 2:
                        //Ver pedidos
                        break;
                    case 3:
                        //Bajar perfil
                        startActivity(new Intent(getApplicationContext(), ModificarClienteActivity.class));
                        break;
                    case 4:
                        //Modificar Perfil
                        startActivity(new Intent(getApplicationContext(), EliminarClienteActivity.class));
                        break;
                }
                break;
        }
    }
}
