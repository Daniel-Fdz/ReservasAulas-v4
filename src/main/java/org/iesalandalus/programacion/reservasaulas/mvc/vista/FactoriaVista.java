package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.texto.VistaTexto;

public enum FactoriaVista {

    GRAFICA{
        @Override
        public IVista crear () {
            return new VistaGrafica();
        }
    },

    TEXTO{
        @Override
        public IVista crear () {
            return new VistaTexto();
        }
    };

    FactoriaVista() {

    }

    public abstract IVista crear ();
}