package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Permanencia implements Serializable {

	private static final long serialVersionUID = -6595137455665251220L;
	private LocalDate dia;
    protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Constructor que acepta un día y un tramo como parámetros
    public Permanencia(LocalDate dia) {
        setDia(dia);
    }

    // Constructor copia
    public Permanencia(Permanencia permanencia) {
        if(permanencia == null) {
            throw new NullPointerException("ERROR: No se puede copiar una permanencia nula.");
        } else {
            setDia(permanencia.getDia());
        }
    }

    // Devuelve el día
    public LocalDate getDia() {
        return dia;
    }

    // Establece el día
    private void setDia(LocalDate dia) {
        if(dia == null) {
            throw new NullPointerException("ERROR: El día de una permanencia no puede ser nulo.");
        } else {
            this.dia = dia;
        }
    }

    public abstract int getPuntos();

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    // Método toString que nos muestra el día y el tramo
    @Override
    public String toString() {
        return "día=" + dia.format(FORMATO_DIA);
    }
}
