package com.isii.systock.Persistencia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.isii.systock.Herramientas.Utils;
import com.isii.systock.Herramientas.VolleySingleton;
import com.isii.systock.Interfaces.Dialogos.VolleyResponse;
import com.isii.systock.Modelo.Producto;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductoPersistencia {

    private static final String TAG = ProductoPersistencia.class.getName();
    private Producto mProducto;
    private Context mContext;
    private VolleyResponse mVolleyResponse;

    public ProductoPersistencia(Context context, VolleyResponse volleyResponse) {

        Utils.initBD(context);
        mProducto = new Producto();
        mContext = context;
        mVolleyResponse = volleyResponse;

    }

    public Producto getProducto() {
        return mProducto;
    }

    public void setProducto(Producto carrito) {
        this.mProducto = carrito;
    }

    /*
    Crea la Tabla Producto
     */
    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s,%s %s %s,%s %s %s,%s %s %s, %s %s %s)",
                Producto.TABLE,
                Producto.KEY_COD_PROD, Utils.INT_TYPE, Utils.NULL_TYPE,
                Producto.KEY_NOMBRE, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Producto.KEY_DESCRIPCION, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Producto.KEY_PRECIO, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Producto.KEY_STOCK, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Producto.KEY_RUBRO, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Producto.KEY_CANT_MINIMA, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }


    /*
    Metodos CRUD
     */
    public void insert(Producto producto) {
        /*SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.insert(Producto.TABLE, null, loadValues(producto));
        DBManager.getInstance().closeDatabase();*/

        String url = "" ;/*Utils.INSERT_PROD + String.format("?cc=%s&n=%s&a=%s&d=%s&c=%s&al=%s&nc=%s&t=%s&p=%s",
                producto.getCodProducto(), producto.getNombre(), producto.getApellido(), producto.getDomicilio(),
                producto.getCorreoElectronico(), producto.getAlias(), nomC, producto.getTipoProducto(),
                String.valueOf(producto.getCodProducto()).substring(String.valueOf(producto.getCodProducto()).length() - 4));*/
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

    public void delete(Producto producto) {
        /*SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Producto.TABLE, Producto.KEY_COD_CLIENTE + " = ?", new String[]{String.valueOf(producto.getCodProducto())});
        DBManager.getInstance().closeDatabase();*/
        String url = Utils.DELETE + "?cc=" + producto.getCodProducto();
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
                            Producto producto = loadData(new JSONObject(response).getJSONObject("producto"));
                            mVolleyResponse.onExisto(producto);
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

           /* mProducto = new Producto();
            SQLiteDatabase db = DBManager.getInstance().openDatabase();
            Cursor cursor = db.rawQuery("select * from " + Producto.TABLE + " where " + Producto.KEY_COD_CLIENTE + " = " + id + "", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                mProducto = loadFromCursor(cursor);
                cursor.close();
            }
            DBManager.getInstance().closeDatabase();
            return mProducto;*/
    }


    public void update(Producto producto) {
        /*SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String id = String.valueOf(producto.getCodProducto());
        String selection = Producto.KEY_COD_CLIENTE + " = " + id;
        db.update(Producto.TABLE, loadValues(producto), selection, null);
        DBManager.getInstance().closeDatabase();*/


        String url = "";/*Utils.UPDATE + String.format("?cc=%s&n=%s&a=%s&d=%s&c=%s&al=%s&nc=%s&t=%s",
                producto.getCodProducto(), producto.getNombre(), producto.getApellido(), producto.getDomicilio(),
                producto.getCorreoElectronico(), producto.getAlias(), nomC, producto.getTipoProducto());*/
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


    public static Producto loadData(JSONObject j) {

        Producto c = new Producto();
        try {
            c.setCodProducto(j.getInt(Producto.KEY_COD_PROD));
            c.setNombre(j.getString(Producto.KEY_NOMBRE));
            c.setDescripcion(j.getString(Producto.KEY_DESCRIPCION));
            c.setPrecio(Float.parseFloat(String.valueOf(j.getDouble(Producto.KEY_PRECIO))));
            c.setRubro(j.getString(Producto.KEY_RUBRO));
            c.setStock(j.getInt(Producto.KEY_STOCK));
            c.setCantidadMinima(j.getInt(Producto.KEY_CANT_MINIMA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return c;

    }


}
