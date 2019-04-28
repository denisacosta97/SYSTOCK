package com.isii.systock.Interfaces.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.isii.systock.Herramientas.PreferenciasManager;
import com.isii.systock.Herramientas.Utils;
import com.isii.systock.Herramientas.Validador;
import com.isii.systock.Interfaces.Actividades.GestionClientes.AnadirClienteActivity;
import com.isii.systock.Modelo.Cliente;
import com.isii.systock.R;
import com.isii.systock.Interfaces.Dialogos.VolleyResponse;

import static android.view.View.GONE;

public class LoginActivity extends AppCompatActivity implements VolleyResponse {

    //Objetos de Android
    Button mBtnLogin, mBtnRegistro;
    ImageView mImgLogo;
    EditText mEditUser, mEditContrasena;
    PreferenciasManager mPreferenciasManager;
    ProgressBar mProgressBar;
    String dni;
    String c;
    //Objetos del Sistema
    Cliente mCliente;
    Validador mValidador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utils.initBD(getApplicationContext());


        mValidador = new Validador();
        mCliente = new Cliente();
        mCliente.setContext(getApplicationContext());
        mCliente.setVolleyResponse(this);
        mPreferenciasManager = new PreferenciasManager(getApplicationContext());

        mBtnLogin = findViewById(R.id.btnLogin);
        mImgLogo = findViewById(R.id.imgLogo);
        mEditUser = findViewById(R.id.editUser);
        mEditContrasena = findViewById(R.id.editPass);
        mProgressBar = findViewById(R.id.progress);
        mBtnRegistro = findViewById(R.id.btnRegister);
        mProgressBar.setVisibility(GONE);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();

            }
        });
        mBtnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

    }

    private void registrar() {
        Intent intent = new Intent(getApplicationContext(), AnadirClienteActivity.class);
        intent.putExtra(Utils.KEY_TIPO, Utils.TIPO_CLIENTE);
        startActivity(intent);
    }

    private void ingresar() {
        dni = mEditUser.getText().toString();
        c = mEditContrasena.getText().toString();

        boolean usuarioCorrecto = mValidador.validarDNI(dni);
        boolean contrasenaCorrecta = mValidador.validarContraseña(c);

        if (usuarioCorrecto && contrasenaCorrecta) {

            mProgressBar.setVisibility(View.VISIBLE);
            mBtnLogin.setVisibility(GONE);

            mCliente.setVolleyResponse(this);
            mCliente.identificarCliente(dni, c);


        } else {
            Toast.makeText(this, "Usuario o contraseña invalidos", Toast.LENGTH_SHORT).show();
            mBtnLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onExisto(Object object) {
        if (object instanceof Cliente) {

            mCliente = (Cliente) object;
            mCliente.setVolleyResponse(this);
            mProgressBar.setVisibility(GONE);

            if (c.equals(String.valueOf(mCliente.getCodCliente()).substring(
                    String.valueOf(mCliente.getCodCliente()).length() - 4))) {

                int tipoUsuario = mCliente.getTipoCliente();

                mPreferenciasManager.setValue(Utils.KEY_TIPO, tipoUsuario);
                mPreferenciasManager.setValue(Utils.KEY_ID, mCliente.getCodCliente());

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra(Utils.KEY_TIPO, tipoUsuario);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                mBtnLogin.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onFallo(String msj) {
        mProgressBar.setVisibility(GONE);
        mBtnLogin.setVisibility(View.VISIBLE);
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}
