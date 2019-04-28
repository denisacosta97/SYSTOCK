package com.isii.systock.Modelo;

import android.content.Context;

import com.isii.systock.Herramientas.Utils;
import com.isii.systock.Interfaces.Dialogos.VolleyResponse;
import com.isii.systock.Persistencia.ClientePersistencia;

public class Cliente {

    public static final String TABLE = "Cliente";
    // Labels Table Columns names
    public static final String KEY_COD_CLIENTE = "codCliente";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_APELLIDO = "apellido";
    public static final String KEY_DOMICILIO = "domicilio";
    public static final String KEY_ALIAS = "alias";
    public static final String KEY_CORREO = "correo";
    public static final String KEY_NOMBRE_COMERCIAL = "nombreComercial";
    public static final String KEY_TIPO_CLIENTE = "tipo";

    private int codCliente, tipoCliente;
    private String nombre, apellido, domicilio, alias, correoElectronico, nombreComercial;
    private Context context;
    private VolleyResponse mVolleyResponse;

    public Cliente() {
        this.tipoCliente = Utils.TYPE_NORMAL;


    }

    public Cliente(int codCliente, String nombre, String apellido, String domicilio, String alias, String correoElectronico, String nombreComercial) {
        this.codCliente = codCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.alias = alias;
        this.correoElectronico = correoElectronico;
        setNombreComercial(nombreComercial);
        this.tipoCliente = Utils.TYPE_NORMAL;
    }

    public VolleyResponse getVolleyResponse() {
        return mVolleyResponse;
    }

    public void setVolleyResponse(VolleyResponse volleyResponse) {
        mVolleyResponse = volleyResponse;
    }

    public int getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(int tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        if (nombreComercial != null)
            this.nombreComercial = nombreComercial;
        else
            this.nombreComercial = "";
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void añadirCliente() {
        new ClientePersistencia(getContext(), getVolleyResponse()).insert(this);
    }

    public void buscarCliente(String id) {
        new ClientePersistencia(getContext(), getVolleyResponse()).get(id, "");
    }

    public void identificarCliente(String user, String pass) {
        new ClientePersistencia(getContext(), getVolleyResponse()).get(user, pass);

    }

    public void eliminarCliente() {
        new ClientePersistencia(getContext(), getVolleyResponse()).delete(this);
    }

    public void listadoClientes() {
        new ClientePersistencia(getContext(), getVolleyResponse()).getAll();
    }

    public void modificarCliente() {
        new ClientePersistencia(getContext(), getVolleyResponse()).update(this);
    }
}
