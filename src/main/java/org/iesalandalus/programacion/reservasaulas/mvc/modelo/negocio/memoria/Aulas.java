package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas {

	private List<Aula> coleccionAulas;
	
	// Constructor por defecto que crea un ArrayList
	public Aulas() {
		coleccionAulas = new ArrayList<Aula>();
	}
	
	// Constructor copia
	public Aulas(IAulas aulas) {
		if(aulas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		} else {
			setAulas(aulas);
		}
	}
	
	// Método que establece las aulas
	private void setAulas(IAulas aulas) {
		if(aulas == null) {
			throw new NullPointerException("ERROR: No se puede establecer aulas nulas.");
		} else {
			coleccionAulas = aulas.getAulas();
		}
	}

	// Método que devuelve una copia profunda de las aulas
	public List<Aula> getAulas() {
		List<Aula> lista = copiaProfundaAulas(coleccionAulas);
		lista.sort(Comparator.comparing(Aula::getNombre));
		return lista;
	}
	
	// Implementación del método de copia profunda
	private List<Aula> copiaProfundaAulas(List<Aula> aulas) {
		List<Aula> copia = new ArrayList<>();
		Iterator<Aula> it = aulas.iterator();
		
		while(it.hasNext()) {
			copia.add(new Aula(it.next()));
		}
		return copia;
	}
	
	// Devuelve el número de aulas
	public int getNumAulas() {
		return coleccionAulas.size();
	}
	
	// Inserta un aula pasada por parámetro
	public void insertar(Aula aula) throws OperationNotSupportedException {
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}
		
		if(coleccionAulas.contains(aula)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
		}
		
		coleccionAulas.add(new Aula(aula));
	}
	
	// Busca un aula y la devuelve
	public Aula buscar(Aula aula) {
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		} else if(coleccionAulas.isEmpty()) {
			return null;
		}
		
		int indice = coleccionAulas.indexOf(aula);
		
		if(coleccionAulas.contains(aula)) {
		  	return new Aula(coleccionAulas.get(indice));
		} 
		return null;		
	}
	
	// Elimina un aula
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		} else if(buscar(aula) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		} else {
			if(coleccionAulas.contains(aula)) {
				coleccionAulas.remove(aula);
			}
		}
	}
	
	// Representación de las aulas
	public List<String> representar() {
		List<String> aulas = new ArrayList<>();
		Iterator<Aula> it = coleccionAulas.iterator();
		
		while(it.hasNext()) {
			aulas.add(it.next().toString());
		}
		return aulas;
	}
}
