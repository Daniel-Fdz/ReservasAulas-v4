package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {

	private List<Profesor> coleccionProfesores;
	
	// Constructor por defecto que crea un ArrayList
	public Profesores() {
		coleccionProfesores = new ArrayList<Profesor>();
	}
	
	// Constructor copia
	public Profesores(IProfesores profesores) {
		if(profesores == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		} else {
			setProfesores(profesores);
		}
	}
	
	// Método que establece los profesores
	private void setProfesores(IProfesores profesores) {
		if(profesores == null) {
			throw new NullPointerException("ERROR: No se pueden establecer profesores nulos.");
		} else {
			coleccionProfesores = profesores.getProfesores();
		}
	}
	
	// Implementación del método de copia profunda
	private List<Profesor> copiaProfundaProfesores(List<Profesor> profesores) {
		List<Profesor> copia = new ArrayList<>();
		Iterator<Profesor> it = profesores.iterator();
		
		while(it.hasNext()) {
			copia.add(new Profesor(it.next()));
		}
		return copia;
	}
	
	// Método que devuelve una copia profunda de los profesores
	public List<Profesor> getProfesores() {
		List<Profesor> lista = copiaProfundaProfesores(coleccionProfesores);
		lista.sort(Comparator.comparing(Profesor::getCorreo));
		return lista;
	}
	
	// Devuelve el número de profesores
	public int getNumProfesores() {
		return coleccionProfesores.size();
	}
	
	// Inserta un profesor pasado por parámetro
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		
		if(coleccionProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		}
		
		coleccionProfesores.add(new Profesor(profesor));
	}
	
	// Busca el profesor pasado por parámetro y lo devuelve
	public Profesor buscar(Profesor profesor) {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		} else if(coleccionProfesores.isEmpty()) {
			return null;
		}
		
		int indice = coleccionProfesores.indexOf(profesor);
		
		if(coleccionProfesores.contains(profesor)) {
		  	return new Profesor(coleccionProfesores.get(indice));
		} else {
			return null;
		}
	}
	
	// Elimina un profesor
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		} else if(buscar(profesor) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese correo.");
		} else {
			coleccionProfesores.remove(profesor);
		}
	}
	
	// Representación de los profesores
	public List<String> representar() {
		List<String> profesores = new ArrayList<>();
		Iterator<Profesor> it = coleccionProfesores.iterator();
		
		while(it.hasNext()) {
			profesores.add(it.next().toString());
		}
		return profesores;
	}
}

