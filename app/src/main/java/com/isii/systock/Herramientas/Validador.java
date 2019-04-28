package com.isii.systock.Herramientas;

public class Validador {

    public boolean validarDNI(String dni){
        boolean isNumber = true;
        try {
            Integer.parseInt(dni);
        }catch (NumberFormatException e){
            isNumber = false;

        }
        return isNumber && dni.length() >= 7;
    }

    public boolean noVacio(String string){
        return string.equals("");
    }
	
	public boolean noVacio(String... datos)
    {
        int i = 0;
        for (i = 0; i<datos.length; i++){
            if (noVacio(datos[i]))
                break;
        }
        return i<(datos.length-1);
    }

    public boolean validarContraseña(String c){
        return c.length() >= 4;
    }
}
