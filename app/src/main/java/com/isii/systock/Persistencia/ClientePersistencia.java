package com.isii.systock.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.isii.systock.Herramientas.Utils;
import com.isii.systock.Herramientas.VolleySingleton;
import com.isii.systock.Interfaces.Dialogos.VolleyResponse;
import com.isii.systock.Modelo.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ClientePersistencia {

    private static final String TAG = ClientePersistencia.class.getName();
    private Cliente mCliente;
    private Context mContext;
    private VolleyResponse mVolleyResponse;

    public ClientePersistencia(Context context, VolleyResponse volleyResponse) {

        Utils.initBD(context);
        mCliente = new Cliente();
        mContext = context;
        mVolleyResponse = volleyResponse;

    }

    public Cliente getCliente() {
        return mCliente;
    }

    public void setCliente(Cliente carrito) {
        this.mCliente = carrito;
    }

    /*
    Crea la Tabla Cliente
     */
    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s,%s %s %s,%s %s %s,%s %s %s, %s %s,%s %s %s)",
                Cliente.TABLE,
                Cliente.KEY_COD_CLIENTE, Utils.INT_TYPE, Utils.NULL_TYPE,
                Cliente.KEY_NOMBRE, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Cliente.KEY_APELLIDO, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Cliente.KEY_DOMICILIO, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Cliente.KEY_CORREO, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Cliente.KEY_ALIAS, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Cliente.KEY_NOMBRE_COMERCIAL, Utils.STRING_TYPE,
                Cliente.KEY_TIPO_CLIENTE, Utils.INT_TYPE, Utils.NULL_TYPE);
    }

    /*
    Carga desde un Cursor los datos de un Cliente
     */
    public Cliente loadFromCursor(Cursor cursor) {
        mCliente = new Cliente();
        mCliente.setCodCliente(cursor.getInt(0));
        mCliente.setNombre(cursor.getString(1));
        mCliente.setApellido(cursor.getString(2));
        mCliente.setDomicilio(cursor.getString(3));
        mCliente.setCorreoElectronico(cursor.getString(4));
        mCliente.setAlias(cursor.getString(5));
        mCliente.setNombreComercial(cursor.getString(6));
        mCliente.setTipoCliente(cursor.getInt(7));

        return mCliente;
    }

    /*
    Carga en un ContentValues los datos de un Cliente
     */
    public ContentValues loadValues(Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put(Cliente.KEY_COD_CLIENTE, cliente.getCodCliente());
        values.put(Cliente.KEY_NOMBRE, cliente.getNombre());
        values.put(Cliente.KEY_APELLIDO, cliente.getApellido());
        values.put(Cliente.KEY_DOMICILIO, cliente.getDomicilio());
        values.put(Cliente.KEY_CORREO, cliente.getCorreoElectronico());
        values.put(Cliente.KEY_ALIAS, cliente.getAlias());
        values.put(Cliente.KEY_NOMBRE_COMERCIAL, cliente.getNombreComercial());
        values.put(Cliente.KEY_TIPO_CLIENTE, cliente.getTipoCliente());

        return values;
    }

    /*
    Metodos CRUD
     */
    public void insert(Cliente cliente) {
        /*SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.insert(Cliente.TABLE, null, loadValues(cliente));
        DBManager.getInstance().closeDatabase();*/
        String nomC = "null";
        assert !cliente.getNombreComercial().equals("");
        nomC = cliente.getNombreComercial();

        String url = Utils.INSERT + String.format("?cc=%s&n=%s&a=%s&d=%s&c=%s&al=%s&nc=%s&t=%s&p=%s",
                cliente.getCodCliente(), cliente.getNombre(), cliente.getApellido(), cliente.getDomicilio(),
                cliente.getCorreoElectronico(), cliente.getAlias(), nomC, cliente.getTipoCliente(),
                String.valueOf(cliente.getCodCliente()).substring(String.valueOf(cliente.getCodCliente()).length() - 4));
        VolleySingleton.getInstance(mContext).addToRequestQueue(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject x = new JSONObject(response);
                    String estado = x.getString("estado");
                    switch (Integer.parseInt(estado)) {
                        case 1:
                            mVolleyResponse.onExisto(null);
                            break;
                        case 2:
                            mVolleyResponse.onFallo("Error al registrar");
                            break;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    mVolleyResponse.onFallo("Error en la Base de Datos");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error Volley: " + error.getMessage());
                mVolleyResponse.onFallo("Error en el Servidor, vuelve a intentar");
            }
        }));


    }

    public void delete(Cliente cliente) {
        /*SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Cliente.TABLE, Cliente.KEY_COD_CLIENTE + " = ?", new String[]{String.valueOf(cliente.getCodCliente())});
        DBManager.getInstance().closeDatabase();*/
        String url = Utils.DELETE + "?cc=" + cliente.getCodCliente();
        // Actualizar datos en el servidor
        VolleySingleton.getInstance(mContext).addToRequestQueue(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject x = new JSONObject(response);
                    String estado = x.getString("estado");
                    switch (Integer.parseInt(estado)) {
                        case 1:
                            mVolleyResponse.onExisto(null);
                            break;
                        case 2:
                            mVolleyResponse.onFallo("Usuario inexistente");
                            break;
                        case 3:
                            mVolleyResponse.onFallo("No se especifico algun componente");
                            break;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    mVolleyResponse.onFallo("Error en la Base de Datos");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error Volley: " + error.getMessage());
                mVolleyResponse.onFallo("Error de autentificación, vuelve a intentar");
            }
        }));
    }


    public void get(String id, String pass) {
        String url = Utils.GET_BY_ID + "?cc=" + id;
        // Actualizar datos en el servidor
        VolleySingleton.getInstance(mContext).addToRequestQueue(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject x = new JSONObject(response);
                    String estado = x.getString("estado");
                    switch (Integer.parseInt(estado)) {
                        case 1:
                            Cliente cliente = loadData(new JSONObject(response).getJSONObject("cliente"));
                            mVolleyResponse.onExisto(cliente);
                            break;
                        case 2:
                            mVolleyResponse.onFallo("Usuario inexistente");
                            break;
                        case 3:
                            mVolleyResponse.onFallo("No se especifico algun componente");
                            break;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    mVolleyResponse.onFallo("Error en la Base de Datos");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error Volley: " + error.getMessage());
                mVolleyResponse.onFallo("Error de autentificación, vuelve a intentar");
            }
        }));

           /* mCliente = new Cliente();
            SQLiteDatabase db = DBManager.getInstance().openDatabase();
            Cursor cursor = db.rawQuery("select * from " + Cliente.TABLE + " where " + Cliente.KEY_COD_CLIENTE + " = " + id + "", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                mCliente = loadFromCursor(cursor);
                cursor.close();
            }
            DBManager.getInstance().closeDatabase();
            return mCliente;*/
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Cliente.TABLE + " where " + Cliente.KEY_COD_CLIENTE + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Cliente cliente) {
        /*SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String id = String.valueOf(cliente.getCodCliente());
        String selection = Cliente.KEY_COD_CLIENTE + " = " + id;
        db.update(Cliente.TABLE, loadValues(cliente), selection, null);
        DBManager.getInstance().closeDatabase();*/
        String nomC = "null";
        assert !cliente.getNombreComercial().equals("");
        nomC = cliente.getNombreComercial();

        String url = Utils.UPDATE + String.format("?cc=%s&n=%s&a=%s&d=%s&c=%s&al=%s&nc=%s&t=%s",
                cliente.getCodCliente(), cliente.getNombre(), cliente.getApellido(), cliente.getDomicilio(),
                cliente.getCorreoElectronico(), cliente.getAlias(), nomC, cliente.getTipoCliente());
        VolleySingleton.getInstance(mContext).addToRequestQueue(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject x = new JSONObject(response);
                    String estado = x.getString("estado");
                    switch (Integer.parseInt(estado)) {
                        case 1:
                            mVolleyResponse.onExisto(null);
                            break;
                        case 2:
                            mVolleyResponse.onFallo("Error al modificar");
                            break;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    mVolleyResponse.onFallo("Error en la Base de Datos");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error Volley: " + error.getMessage());
                mVolleyResponse.onFallo("Error en el Servidor, vuelve a intentar");
            }
        }));
    }

    public void getAll() {

        /*SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Cliente> list = new ArrayList<Cliente>();
        String query = String.format("select * from %s", Cliente.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mCliente = loadFromCursor(cursor);
                list.add(mCliente);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;*/
        VolleySingleton.getInstance(mContext).addToRequestQueue(new StringRequest(Request.Method.GET, Utils.GET_ALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject x = new JSONObject(response);
                    String estado = x.getString("estado");
                    switch (Integer.parseInt(estado)) {
                        case 1:
                            ArrayList<Cliente> list = loadListData(x.getJSONArray("cliente"));
                            mVolleyResponse.onExisto(list);
                            break;
                        case 2:
                            mVolleyResponse.onFallo("Error al obtener usuarios");
                            break;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    mVolleyResponse.onFallo("Error en la Base de Datos");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error Volley: " + error.getMessage());
                mVolleyResponse.onFallo("Error en el Servidor, vuelve a intentar");
            }
        }));

    }

    public static Cliente loadData(JSONObject j) {

        Cliente c = new Cliente();
        try {
            c.setCodCliente(j.getInt(Cliente.KEY_COD_CLIENTE));
            c.setNombre(j.getString(Cliente.KEY_NOMBRE));
            c.setApellido(j.getString(Cliente.KEY_APELLIDO));
            c.setDomicilio(j.getString(Cliente.KEY_DOMICILIO));
            c.setCorreoElectronico(j.getString(Cliente.KEY_CORREO));
            c.setAlias(j.getString(Cliente.KEY_ALIAS));
            c.setNombreComercial(j.getString(Cliente.KEY_NOMBRE_COMERCIAL));
            c.setTipoCliente(j.getInt(Cliente.KEY_TIPO_CLIENTE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return c;

    }

    public static ArrayList<Cliente> loadListData(JSONArray x) {

        Cliente c = null;
        ArrayList<Cliente> list = new ArrayList<Cliente>();
        for (int i = 0; i < x.length(); i++) {
            try {
                JSONObject j = x.getJSONObject(i);
                c = new Cliente();
                c.setCodCliente(j.getInt(Cliente.KEY_COD_CLIENTE));
                c.setNombre(j.getString(Cliente.KEY_NOMBRE));
                c.setApellido(j.getString(Cliente.KEY_APELLIDO));
                c.setDomicilio(j.getString(Cliente.KEY_DOMICILIO));
                c.setCorreoElectronico(j.getString(Cliente.KEY_CORREO));
                c.setAlias(j.getString(Cliente.KEY_ALIAS));
                c.setNombreComercial(j.getString(Cliente.KEY_NOMBRE_COMERCIAL));
                c.setTipoCliente(j.getInt(Cliente.KEY_TIPO_CLIENTE));

                list.add(c);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;

    }

}
