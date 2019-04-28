package com.isii.systock.Interfaces.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.isii.systock.Modelo.Opciones;
import com.isii.systock.R;

import java.util.ArrayList;


public class OpcionesAdapter extends RecyclerView.Adapter<OpcionesAdapter.BaresViewHolder> {

    private ArrayList<Opciones> arrayList;
    private Context context;

    public OpcionesAdapter(ArrayList<Opciones> list, Context ctx) {
        context = ctx;
        arrayList = list;

    }


    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getId();
    }

    @NonNull
    @Override
    public BaresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opciones, parent, false);


        return new BaresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaresViewHolder holder, int position) {

        Opciones s = arrayList.get(position);

        holder.titulo.setText(s.getNombre());
        Glide.with(holder.img.getContext())
                .load(s.getLogo())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class BaresViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        ImageView img;

        BaresViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgLogo);
            titulo = itemView.findViewById(R.id.txtTitulo);


        }
    }

}
