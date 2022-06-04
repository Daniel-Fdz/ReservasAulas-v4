package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class MainApp {

    public static void main(String[] args) {
        IModelo modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
        IVista vista = argumentosVista(args);
        IControlador controlador = new Controlador(modelo, vista);
        controlador.comenzar();
    }

    private static IVista argumentosVista(String[] args) {
        IVista vista = FactoriaVista.GRAFICA.crear();
        for (String argumento : args) {
            if (argumento.equalsIgnoreCase("-vGrafica") ) {
                vista = FactoriaVista.GRAFICA.crear();
            } else if (argumento.equalsIgnoreCase("-vTexto")) {
                vista = FactoriaVista.TEXTO.crear();
            }
        }
        return vista;
    }

}