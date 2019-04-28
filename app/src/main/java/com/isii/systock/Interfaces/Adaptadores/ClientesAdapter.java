package com.isii.systock.Interfaces.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isii.systock.Modelo.Cliente;
import com.isii.systock.R;

import java.util.ArrayList;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClientesViewHolder> {

    private ArrayList<Cliente> arrayList;
    private Context context;

    public ClientesAdapter(ArrayList<Cliente> list, Context ctx) {
        context = ctx;
        arrayList = list;

    }


    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getCodCliente();
    }

    @NonNull
    @Override
    public ClientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clientes, parent, false);


        return new ClientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientesViewHolder holder, int position) {

        Cliente s = arrayList.get(position);

        holder.codCliente.setText(s.getCodCliente() + "");
        holder.nombre.setText(s.getNombre() + " " + s.getApellido());
        holder.correo.setText(s.getCorreoElectronico());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ClientesViewHolder extends RecyclerView.ViewHolder {

        TextView codCliente, nombre, correo;

        ClientesViewHolder(View itemView) {
            super(itemView);

            codCliente = itemView.findViewById(R.id.txtCodCliente);
            nombre = itemView.findViewById(R.id.txtNombre);
            correo = itemView.findViewById(R.id.txtCorreo);


        }
    }

}
