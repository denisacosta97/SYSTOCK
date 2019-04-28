package com.isii.systock.Interfaces.Actividades.GestionClientes;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class EliminarClienteActivity extends AppCompatActivity implements DialogInterface {


    //Objetos de Android
    EditText mEditCodigoCliente;
    TextView mTxtCod, mTxtNombre, mTxtCorreo, mTxtDomicilio, mTxtInfo;
    Button mBtnBuscar, mBtnBorrar;
    CardView mCardUser;
    LinearLayout mLinearExiste, mLinearNoExiste, mLinearAdmin;
    PreferenciasManager mPreferenciasManager;
    Toolbar mToolbar;
    DialogInterface listener;
    DialogoAdvertencia advertencia;
    VolleyResponse listenerBuscar, listenerEliminar;
    //Objetos del Sistema
    Cliente mCliente;
    Validador mValidador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_cliente);

        agregarToolbar();
        listener = this;

        mPreferenciasManager = new PreferenciasManager(getApplicationContext());

        mEditCodigoCliente = findViewById(R.id.editCodC);
        mBtnBorrar = findViewById(R.id.btnEliminar);
        mBtnBuscar = findViewById(R.id.btnLogin);
        mCardUser = findViewById(R.id.cardUser);
        mLinearExiste = findViewById(R.id.linearExistente);
        mLinearNoExiste = findViewById(R.id.linearNoExiste);
        mLinearAdmin = findViewById(R.id.linearAdmin);
        mTxtCod = findViewById(R.id.txtCodCliente);
        mTxtCorreo = findViewById(R.id.txtCorreo);
        mTxtNombre = findViewById(R.id.txtNombre);
        mTxtDomicilio = findViewById(R.id.txtDomicilio);
        mTxtInfo = findViewById(R.id.txtInfo);


        mBtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

        mBtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        listenerBuscar = new VolleyResponse() {
            @Override
            public void onExisto(Object object) {
                if (object instanceof Cliente) {
                    mCliente = (Cliente) object;
                    if (mCliente.getCodCliente() != 0) {

                        mCardUser.setVisibility(View.VISIBLE);
                        mLinearExiste.setVisibility(View.VISIBLE);
                        mLinearNoExiste.setVisibility(GONE);

                        cargarDatos(mCliente);

                    } else {
                        mCardUser.setVisibility(View.VISIBLE);
                        mLinearExiste.setVisibility(View.GONE);
                        mLinearNoExiste.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFallo(String msj) {
                Toast.makeText(EliminarClienteActivity.this, msj, Toast.LENGTH_SHORT).show();

            }
        };

        listenerEliminar = new VolleyResponse() {
            @Override
            public void onExisto(Object object) {
                Toast.makeText(EliminarClienteActivity.this, "¡Usuario eliminado!", Toast.LENGTH_SHORT).show();
                if (mPreferenciasManager.getValueInt(Utils.KEY_TIPO) == Utils.TYPE_NORMAL) {
                    finishAffinity();
                }
                finish();

            }

            @Override
            public void onFallo(String msj) {
                Toast.makeText(EliminarClienteActivity.this, msj, Toast.LENGTH_SHORT).show();

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

    private void updateUI() {
        int tipoUsuario = mPreferenciasManager.getValueInt(Utils.KEY_TIPO);
        switch (tipoUsuario) {
            case Utils.TYPE_ADMIN:
                mLinearAdmin.setVisibility(View.VISIBLE);
                mLinearExiste.setVisibility(GONE);
                mLinearNoExiste.setVisibility(GONE);
                mCardUser.setVisibility(GONE);
                break;
            case Utils.TYPE_NORMAL:
                mLinearAdmin.setVisibility(View.GONE);
                mLinearExiste.setVisibility(View.VISIBLE);
                mLinearNoExiste.setVisibility(GONE);
                mCardUser.setVisibility(View.GONE);
                mTxtInfo.setVisibility(View.VISIBLE);
                mCliente = new Cliente();
                mCliente.setVolleyResponse(listenerBuscar);
                mCliente.buscarCliente(String.valueOf(mPreferenciasManager.getValueInt(Utils.KEY_ID)));
                break;
        }
    }

    private void eliminar() {
        advertencia = new DialogoAdvertencia();
        advertencia.loadListener(listener);
        if (getIntent().getIntExtra(Utils.KEY_TIPO, -1) != Utils.TIPO_CLIENTE)
            advertencia.setTitulo("¿Eliminar cliente?");
        else
            advertencia.setTitulo("¿Eliminar perfil?");
        advertencia.show(getSupportFragmentManager(), "dialog");


    }

    private void buscar() {

        mValidador = new Validador();

        String id = mEditCodigoCliente.getText().toString();

        if (!mValidador.noVacio(id)) {
            mCliente = new Cliente();
            mCliente.setContext(getApplicationContext());
            mCliente.setVolleyResponse(listenerBuscar);
            mCliente.buscarCliente(id);
        } else {
            Toast.makeText(this, "Introduce un numero de Cliente", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatos(Cliente mCliente) {
        mTxtCod.setText(mCliente.getCodCliente() + "");
        mTxtNombre.setText(mCliente.getNombre());
        mTxtCorreo.setText(mCliente.getCorreoElectronico());
        mTxtDomicilio.setText(mCliente.getDomicilio());
    }

    @Override
    public void confirmar() {
        if (mCliente != null) {
            mCliente.setVolleyResponse(listenerEliminar);
            mCliente.eliminarCliente();

        }
    }

    @Override
    public void cancelar() {
        advertencia.dismiss();
    }
}
