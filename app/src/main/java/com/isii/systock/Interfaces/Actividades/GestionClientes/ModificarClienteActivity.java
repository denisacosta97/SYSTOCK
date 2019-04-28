package com.isii.systock.Interfaces.Actividades.GestionClientes;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.isii.systock.Herramientas.PreferenciasManager;
import com.isii.systock.Herramientas.Utils;
import com.isii.systock.Herramientas.Validador;
import com.isii.systock.Interfaces.Dialogos.DialogInterface;
import com.isii.systock.Interfaces.Dialogos.DialogoAdvertencia;
import com.isii.systock.Interfaces.Dialogos.VolleyResponse;
import com.isii.systock.Modelo.Cliente;
import com.isii.systock.R;

import static android.view.View.GONE;

public class ModificarClienteActivity extends AppCompatActivity implements DialogInterface {

    //Objetos de Android
    EditText mEditCod, mEditNombre, mEditApellido, mEditDomicilio, mEditCorreo, mEditAlias, mEditNombreC, mEditCodBuscar;
    Button mBtnModificar, mBtnBuscar;
    LinearLayout mLinearAdmin, mLinearNoExiste, mLinearExiste;
    PreferenciasManager mPreferenciasManager;
    Toolbar mToolbar;
    DialogInterface dialogInterface;
    DialogoAdvertencia advertencia;
    VolleyResponse listenerBuscar, listenerModificar;
    //Objetos del Sistema
    Cliente mCliente;
    Validador mValidador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cliente);

        agregarToolbar();
        dialogInterface = this;

        mPreferenciasManager = new PreferenciasManager(getApplicationContext());

        mBtnModificar = findViewById(R.id.btnAñadir);
        mBtnBuscar = findViewById(R.id.btnLogin);
        mEditCodBuscar = findViewById(R.id.editCodCl);
        mEditCod = findViewById(R.id.editCodC);
        mEditNombre = findViewById(R.id.editNombre);
        mEditAlias = findViewById(R.id.editAlias);
        mEditApellido = findViewById(R.id.editApellido);
        mEditDomicilio = findViewById(R.id.editDomicilio);
        mEditCorreo = findViewById(R.id.editCorreo);
        mEditNombreC = findViewById(R.id.editNombreC);
        mLinearExiste = findViewById(R.id.linearExistente);
        mLinearNoExiste = findViewById(R.id.linearNoExiste);
        mLinearAdmin = findViewById(R.id.linearAdmin);

        mBtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

        mBtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();

            }
        });


        listenerModificar = new VolleyResponse() {
            @Override
            public void onExisto(Object object) {
                Toast.makeText(ModificarClienteActivity.this, "¡Usuario modificado!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFallo(String msj) {
                Toast.makeText(ModificarClienteActivity.this, msj, Toast.LENGTH_SHORT).show();
                advertencia.dismiss();

            }
        };

        listenerBuscar = new VolleyResponse() {
            @Override
            public void onExisto(Object object) {
                if (object instanceof Cliente) {
                    mCliente = (Cliente) object;
                    if (mCliente.getCodCliente() != 0) {

                        mLinearExiste.setVisibility(View.VISIBLE);
                        mLinearNoExiste.setVisibility(View.GONE);
                        cargarDatos(mCliente);

                    } else {

                        mLinearNoExiste.setVisibility(View.VISIBLE);
                        mLinearExiste.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFallo(String msj) {
                Toast.makeText(ModificarClienteActivity.this, msj, Toast.LENGTH_SHORT).show();

            }
        };
        updateUI();


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

    private void modificar() {
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
            advertencia.setTitulo("¿Modificar usuario?");
            advertencia.show(getSupportFragmentManager(), "dialog");
        } else {
            Toast.makeText(this, "¡Complete los campos vacios!", Toast.LENGTH_SHORT).show();
        }


    }


    private void buscar() {
        String usuario = mEditCodBuscar.getText().toString();

        mValidador = new Validador();

        if (!mValidador.noVacio(usuario)) {

            mCliente = new Cliente();
            mCliente.setVolleyResponse(listenerBuscar);
            mCliente.buscarCliente(usuario);

        } else {
            Toast.makeText(this, "¡Ingrese un codigo de usuario valido!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        int tipoUsuario = mPreferenciasManager.getValueInt(Utils.KEY_TIPO);
        switch (tipoUsuario) {
            case Utils.TYPE_ADMIN:
                mLinearAdmin.setVisibility(View.VISIBLE);
                mLinearExiste.setVisibility(GONE);
                mLinearNoExiste.setVisibility(GONE);
                break;
            case Utils.TYPE_NORMAL:
                mLinearAdmin.setVisibility(View.GONE);
                mLinearExiste.setVisibility(View.VISIBLE);
                mLinearNoExiste.setVisibility(GONE);
                mCliente = new Cliente();
                mCliente.setVolleyResponse(listenerBuscar);
                mCliente.buscarCliente(String.valueOf(mPreferenciasManager.getValueInt(Utils.KEY_ID)));
                break;
        }
    }

    private void cargarDatos(Cliente mCliente) {
        mEditCod.setText(mCliente.getCodCliente() + "");
        mEditNombre.setText(mCliente.getNombre());
        mEditAlias.setText(mCliente.getAlias());
        mEditApellido.setText(mCliente.getApellido());
        mEditDomicilio.setText(mCliente.getDomicilio());
        mEditCorreo.setText(mCliente.getCorreoElectronico());
        mEditNombreC.setText(mCliente.getNombreComercial());
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
        mCliente.setVolleyResponse(listenerModificar);
        mCliente.setContext(getApplicationContext());
        mCliente.modificarCliente();


    }

    @Override
    public void cancelar() {

        advertencia.dismiss();
    }

}
