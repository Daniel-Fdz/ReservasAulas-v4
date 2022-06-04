package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Aula implements Serializable {

	private static final long serialVersionUID = 9002512225335087751L;
	private static final float PUNTOS_POR_PUESTO = 0.5f;
    private static final int MIN_PUESTOS = 10;
    private static final int MAX_PUESTOS = 60;
    private String nombre;
    private int puestos;

    // Constructor que acepta un nombre de aula como parámetro
    public Aula(String nombre, int puestos) {
        setNombre(nombre);
        setPuestos(puestos);
    }

    // Constructor copia
    public Aula(Aula aula) {
        if(aula == null) {
            throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
        } else {
            setNombre(aula.getNombre());
            setPuestos(aula.getPuestos());
        }
    }

    // Devuelve el nombre del aula
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre del aula
    public void setNombre(String nombre) {
        if(nombre == null) {
            throw new NullPointerException("ERROR: El nombre del aula no puede ser nulo.");
        } else if(nombre.trim().equals("")) {
            throw new IllegalArgumentException("ERROR: El nombre del aula no puede estar vacío.");
        } else {
            this.nombre = nombre;
        }
    }

    // Devuelve el número  de puestos
    public int getPuestos() {
        return puestos;
    }

    // Establece el número de puestos sin pasar de un mínimo y un máximo
    private void setPuestos(int puestos) {
        if(puestos < MIN_PUESTOS || puestos > MAX_PUESTOS) {
            throw new IllegalArgumentException("ERROR: El número de puestos no es correcto.");
        } else {
            this.puestos = puestos;
        }
    }

    // Devuelve los puntos
    public float getPuntos() {
        return PUNTOS_POR_PUESTO * getPuestos();
    }

    // Devuelve un aula ficticia con 30 puestos
    public static Aula getAulaFicticia(String aula) {
        return new Aula(aula, 30);
    }

    // Métodos hashCode/equals
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aula other = (Aula) obj;
        return Objects.equals(nombre, other.nombre);
    }

    // Método toString que nos muestra el nombre del aula
    @Override
    public String toString() {
        return "nombre=" + nombre + ", puestos=" + puestos;
    }
}
