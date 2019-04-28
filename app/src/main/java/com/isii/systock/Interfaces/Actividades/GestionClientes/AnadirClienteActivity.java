package com.isii.systock.Interfaces.Actividades.GestionClientes;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.isii.systock.Herramientas.Utils;
import com.isii.systock.Herramientas.Validador;
import com.isii.systock.Interfaces.Dialogos.DialogInterface;
import com.isii.systock.Interfaces.Dialogos.DialogoAdvertencia;
import com.isii.systock.Interfaces.Dialogos.VolleyResponse;
import com.isii.systock.Modelo.Cliente;
import com.isii.systock.R;

public class AnadirClienteActivity extends AppCompatActivity implements DialogInterface, VolleyResponse {

    //Objetos de Android
    EditText mEditCod, mEditNombre, mEditApellido, mEditDomicilio, mEditCorreo, mEditAlias, mEditNombreC;
    Button mBtnAnadir;
    Toolbar mToolbar;
    TextView mTxtInfo;
    DialogInterface dialogInterface;
    DialogoAdvertencia advertencia;
    //Objetos del Sistema
    Cliente mCliente;
    Validador mValidador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cliente);

        dialogInterface = this;
        agregarToolbar();

        mBtnAnadir = findViewById(R.id.btnAñadir);
        mEditCod = findViewById(R.id.editCodC);
        mEditNombre = findViewById(R.id.editNombre);
        mEditAlias = findViewById(R.id.editAlias);
        mEditApellido = findViewById(R.id.editApellido);
        mEditDomicilio = findViewById(R.id.editDomicilio);
        mEditCorreo = findViewById(R.id.editCorreo);
        mEditNombreC = findViewById(R.id.editNombreC);
        mTxtInfo = findViewById(R.id.txtInfo);

        mBtnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadir();
            }
        });
        if (getIntent().getIntExtra(Utils.KEY_TIPO, -1) == Utils.TIPO_CLIENTE) {
            mTxtInfo.setVisibility(View.VISIBLE);
            mBtnAnadir.setText("REGISTRAR");
        }

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
        String nombre, apellido, domicilio, correo, alias, nombreComercial, codigoCliente;

        codigoCliente = mEditCod.getText().toString();
        nombre = mEditNombre.getText().toString();
        apellido = mEditApellido.getText().toString();
        domicilio = mEditDomicilio.getText().toString();
        alias = mEditAlias.getText().toString();
        correo = mEditCorreo.getText().toString();
        nombreComercial = mEditNombreC.getText().toString();

        mValidador = new Validador();

        String[] datos = {codigoCliente, nombre, apellido, domicilio, alias, correo, nombreComercial};

        if (!mValidador.noVacio(datos)) {
            advertencia = new DialogoAdvertencia();
            advertencia.loadListener(dialogInterface);
            if (getIntent().getIntExtra(Utils.KEY_TIPO, -1) != Utils.TIPO_CLIENTE)
                advertencia.setTitulo("¿Añadir nuevo cliente?");
            else
                advertencia.setTitulo("Confirmar registro");
            advertencia.show(getSupportFragmentManager(), "dialog");

        } else {
            Toast.makeText(this, "¡Complete los campos vacios!", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void confirmar() {
        String nombre, apellido, domicilio, correo, alias, nombreComercial, codigoCliente;

        codigoCliente = mEditCod.getText().toString();
        nombre = mEditNombre.getText().toString();
        apellido = mEditApellido.getText().toString();
        domicilio = mEditDomicilio.getText().toString();
        alias = mEditAlias.getText().toString();
        correo = mEditCorreo.getText().toString();
        nombreComercial = mEditNombreC.getText().toString();


        mCliente = new Cliente(Integer.parseInt(codigoCliente), nombre, apellido, domicilio, alias, correo, nombreComercial);
        mCliente.setContext(getApplicationContext());
        mCliente.setVolleyResponse(this);
        mCliente.añadirCliente();

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
