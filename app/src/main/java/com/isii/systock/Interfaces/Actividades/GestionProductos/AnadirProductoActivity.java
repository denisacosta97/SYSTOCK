package com.isii.systock.Interfaces.Actividades.GestionProductos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.isii.systock.Herramientas.Validador;
import com.isii.systock.Interfaces.Dialogos.DialogInterface;
import com.isii.systock.Interfaces.Dialogos.DialogoAdvertencia;
import com.isii.systock.Interfaces.Dialogos.VolleyResponse;
import com.isii.systock.Modelo.Producto;
import com.isii.systock.R;

public class AnadirProductoActivity extends AppCompatActivity implements DialogInterface, VolleyResponse {

    //Objetos de Android
    EditText mEditCod, mEditNombre, mEditDescripcion, mEditPrecio, mEditStock, mEditMinimo;
    Button mBtnAnadir;
    Toolbar mToolbar;
    Spinner mSpinnerRubro;
    DialogInterface dialogInterface;
    DialogoAdvertencia advertencia;
    ArrayAdapter<String> mArrayAdapter;
    //Objetos del Sistema
    Producto mProducto;
    Validador mValidador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producto);

        dialogInterface = this;
        agregarToolbar();

        mBtnAnadir = findViewById(R.id.btnAñadir);
        mEditCod = findViewById(R.id.editCodC);
        mEditNombre = findViewById(R.id.editNombre);
        mEditDescripcion = findViewById(R.id.editDescrip);
        mEditPrecio = findViewById(R.id.editPrecio);
        mEditStock = findViewById(R.id.editStock);
        mEditMinimo = findViewById(R.id.editCantMin);
        mSpinnerRubro = findViewById(R.id.spinnerRubro);

        mBtnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadir();
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void añadir() {
        String nombre, descripcion, precio, stock, cantMinima, codigoProducto;

        codigoProducto = mEditCod.getText().toString();
        nombre = mEditNombre.getText().toString();
        descripcion = mEditDescripcion.getText().toString();
        precio = mEditPrecio.getText().toString();
        stock = mEditStock.getText().toString();
        cantMinima = mEditMinimo.getText().toString();

        mValidador = new Validador();

        String[] datos = {codigoProducto, nombre, descripcion, precio, stock, cantMinima};

        if (!mValidador.noVacio(datos)) {
            advertencia = new DialogoAdvertencia();
            advertencia.loadListener(dialogInterface);
            advertencia.setTitulo("Confirmar registro");
            advertencia.show(getSupportFragmentManager(), "dialog");

        } else {
            Toast.makeText(this, "¡Complete los campos vacios!", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void confirmar() {
        String nombre, descripcion, precio, stock, cantMinima, codigoProducto;

        codigoProducto = mEditCod.getText().toString();
        nombre = mEditNombre.getText().toString();
        descripcion = mEditDescripcion.getText().toString();
        precio = mEditPrecio.getText().toString();
        stock = mEditStock.getText().toString();
        cantMinima = mEditMinimo.getText().toString();


        mProducto = new Producto(Integer.parseInt(codigoProducto),Integer.parseInt(stock),""
                ,Integer.parseInt(cantMinima),nombre,descripcion,Float.parseFloat(precio));
        mProducto.setContext(getApplicationContext());
        mProducto.setVolleyResponse(this);
        mProducto.añadirProducto();

    }

    @Override
    public void cancelar() {
        advertencia.dismiss();

    }

    @Override
    public void onExisto(Object object) {
        Toast.makeText(this, "¡Registrado!", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onFallo(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
        advertencia.dismiss();

    }
}
