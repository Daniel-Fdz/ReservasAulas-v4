package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Reserva implements Serializable {

	private static final long serialVersionUID = 7191019775875858084L;
	private Profesor profesor;
    private Aula aula;
    private Permanencia permanencia;

    // Constructor que acepta un  profesor, un aula y una permanencia como parámetros
    public Reserva(Profesor profesor, Aula aula, Permanencia permanencia) {
        setProfesor(profesor);
        setAula(aula);
        setPermanencia(permanencia);
    }

    // Constructor copia
    public Reserva(Reserva reserva) {
        if(reserva == null) {
            throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
        } else {
            setProfesor(reserva.getProfesor());
            setAula(reserva.getAula());
            setPermanencia(reserva.getPermanencia());
        }
    }

    // Establece un profesor para la reserva
    private void setProfesor(Profesor profesor) {
        if(profesor == null) {
            throw new NullPointerException("ERROR: La reserva debe estar a nombre de un profesor.");
        }
        this.profesor = new Profesor(profesor);
    }

    // Devuelve un profesor
    public Profesor getProfesor() {
        return new Profesor(profesor);
    }

    // Establece un aula para la reserva
    private void setAula(Aula aula) {
        if(aula == null) {
            throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
        }
        this.aula = new Aula(aula);
    }

    // Devuelve un aula
    public Aula getAula() {
        return new Aula(aula);
    }

    // Establece una permanencia para la reserva
    private void setPermanencia(Permanencia permanencia) {
        if(permanencia == null) {
            throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
        } else if(permanencia instanceof PermanenciaPorHora) {
            this.permanencia = new PermanenciaPorHora((PermanenciaPorHora) permanencia);
        } else {
            this.permanencia = new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
        }
    }

    // Devuelve una permanencia
    public Permanencia getPermanencia() {
        if(this.permanencia instanceof PermanenciaPorHora) {
            return new PermanenciaPorHora((PermanenciaPorHora) this.permanencia);
        } else {
            return new PermanenciaPorTramo((PermanenciaPorTramo) this.permanencia);
        }
    }

    public static Reserva getReservaFicticia(Aula aula, Permanencia permanencia) {
        return new Reserva(Profesor.getProfesorFicticio("profe@mail.com"), aula, permanencia);
    }

    public float getPuntos() {
        return aula.getPuntos() + permanencia.getPuntos();
    }

    @Override
    public int hashCode() {
        return Objects.hash(aula, permanencia);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reserva other = (Reserva) obj;
        return Objects.equals(aula, other.aula) && Objects.equals(permanencia, other.permanencia);
    }

    // Método toString que muestra el nombre, el correo, el aula y la permanencia de la reserva
    @Override
    public String toString() {
        return profesor + ", " + aula + ", " + permanencia.toString() +", puntos=" + Float.toString(getPuntos()).replace(".", ",");
    }
}