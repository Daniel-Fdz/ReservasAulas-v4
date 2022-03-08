package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

public enum Tramo {

	MANANA("Mañana"), TARDE("Tarde");
	
	private String cadenaAMostrar;
	
	// Constructor del enum que asigna la cadena a mostrar
	private Tramo(String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}
	
	// Método toString que devuelve la cadena
	@Override
	public String toString() {
		return cadenaAMostrar;
	}
}
