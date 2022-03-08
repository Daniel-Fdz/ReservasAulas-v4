package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.util.Objects;

public class Profesor {
	
	private final static String ER_TELEFONO = "[69]\\d{8}";
	private final static String ER_CORREO = "^\\w+[.]?\\w+[@]\\w+[.]\\w{2,5}$";
	private String nombre;
	private String correo;
	private String telefono;
	
	// Constructor que acepta un nombre y un correo de un profesor como parámetros
	public Profesor(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
	}
	
	// Constructor que acepta un nombre, un correo y un teléfono de un profesor como parámetros
	public Profesor(String nombre, String correo, String telefono) {
		setNombre(nombre);
		setCorreo(correo);
		setTelefono(telefono);
	}
	
	// Constructor copia
	public Profesor(Profesor profesor) {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		} else {
			setNombre(profesor.getNombre());
			setCorreo(profesor.getCorreo());
			setTelefono(profesor.getTelefono());
		}
	}

	// Devuelve el nombre del profesor
	public String getNombre() {
		return nombre;
	}

	// Establece el nombre del profesor
	private void setNombre(String nombre) {
		if(nombre == null) {
			throw new NullPointerException("ERROR: El nombre del profesor no puede ser nulo.");
		} else if(nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre del profesor no puede estar vacío.");
		} else {
			this.nombre = formateaNombre(nombre);
		}
	}
	
	private String formateaNombre(String nombre) {
		if(nombre == null || nombre.equals("")) {
			throw new NullPointerException("ERROR: El nombre no puede estar vacío.");
		} else {
			boolean hayEspacio = true;
			char[] caracteres = nombre.toCharArray();
			
			for(int i = 0; i < caracteres.length; i++) {
				if(Character.isLetter(caracteres[i])) {
					if(hayEspacio) {
						caracteres[i] = Character.toUpperCase(caracteres[i]);
						hayEspacio = false;
					}
				} else {
					hayEspacio = true;
				}
			}
			return nombre = String.valueOf(caracteres);
		}
	}

	// Devuelve el correo del profesor
	public String getCorreo() {
		return correo;
	}

	// Establece el correo del profesor
	public void setCorreo(String correo) {
		if(correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		} else if(!correo.matches(ER_CORREO) || correo.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		} else {
			this.correo = correo;
		}
	}

	// Devuelve el teléfono del profesor
	public String getTelefono() {
		return telefono;
	}

	//Establece el teléfono del profesor
	public void setTelefono(String telefono) {
		if(telefono != null && !telefono.matches(ER_TELEFONO) || telefono.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El teléfono del profesor no es válido.");
		} else {
			this.telefono = telefono;
		}
	}

	public static Profesor getProfesorFicticio(String correo) {
		if(correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		} else if(!correo.matches(ER_CORREO) || correo.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		} else {
			return new Profesor("Laura", correo, "666555444");
		}
	}
	
	// Métodos hashCode/equals
	@Override
	public int hashCode() {
		return Objects.hash(correo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(correo, other.correo);
	}

	// Método toString que muestra el teléfono, el nombre y el correo del profesor.
	// En caso de no estar el teléfono, no se mostrará.
	@Override
	public String toString() {
		String ponerTelefono = (telefono == null) ? "" : ", teléfono=" + telefono; 
		return "nombre=" + nombre + ", correo=" + correo + ponerTelefono;
	}
}
