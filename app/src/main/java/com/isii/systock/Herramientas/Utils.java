package com.isii.systock.Herramientas;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.isii.systock.Persistencia.BDGestor;
import com.isii.systock.Persistencia.DBManager;

public class Utils {

    //Constantes para las BD
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String FLOAT_TYPE = "float";
    public static final String NULL_TYPE = "not null";
    public static final String AUTO_INCREMENT = "primary key autoincrement";
    public static final String ONLINE_MODE = "mode_online";

    public static final String KEY_TIPO = "tipoUser";
    public static final String KEY_ID = "idUser";
    public static final int TYPE_NORMAL = 100;
    public static final int TYPE_ADMIN = 200;
    public static final String PREF_NAME = "pref_systock";

    public static final int TIPO_CLIENTE = 110;

    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = "3306";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "http://192.168.43.53";
    /**
     * URLs del Web Service
     */
    public static final String GET_ALL = IP +"/systock/cliente/list.php";
    public static final String GET_BY_ID = IP +"/systock/cliente/byId.php";
    public static final String UPDATE = IP  + "/systock/cliente/actualizar.php";
    public static final String DELETE = IP  + "/systock/cliente/borrar.php";
    public static final String INSERT = IP + "/systock/cliente/insertar.php";

    public static final String GET_PROD_BY_ID = IP +"/systock/producto/byId.php";
    public static final String UPDATE_PROD = IP  + "/systock/producto/actualizar.php";
    public static final String DELETE_PROD = IP  + "/systock/producto/borrar.php";
    public static final String INSERT_PROD = IP + "/systock/producto/insertar.php";

    //Metodo para cambiar el color a un drawable
    public static Drawable changeColorDrawable(int draw, Context c, int color) {
        Drawable top = ResourcesCompat.getDrawable(c.getResources(), draw, null);
        assert top != null;
        top.setColorFilter(new PorterDuffColorFilter(c.getResources().getColor(color),
                PorterDuff.Mode.SRC_IN));

        return top;
    }

    public static void initBD(Context context) {
        BDGestor gestor = new BDGestor(context);
        DBManager.initializeInstance(gestor);
    }

}
