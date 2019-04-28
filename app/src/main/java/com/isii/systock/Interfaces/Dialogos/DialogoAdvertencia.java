package com.isii.systock.Interfaces.Dialogos;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.isii.systock.R;

public class DialogoAdvertencia extends DialogFragment {

    View view;
    TextView txtTitulo;
    Button btnSi, btnNo;
    DialogInterface listener;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void loadListener(DialogInterface p) {
        listener = p;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflo la vista
        view = inflater.inflate(R.layout.dialog_interface, container, false);
        // Remuevo la opcion de titulo del Dialogo
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Declaracion de botones
        loadViews();

        loadData();


        return view;
    }

    private void loadData() {
        txtTitulo.setText(titulo);
        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.confirmar();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.cancelar();
            }
        });
    }


    private void loadViews() {
        btnNo = view.findViewById(R.id.btnNo);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        btnSi = view.findViewById(R.id.btnSi);
    }


}
