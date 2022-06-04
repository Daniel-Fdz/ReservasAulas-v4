package org.iesalandalus.programacion.reservasaulas.mvc.vista.texto;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

import javax.naming.OperationNotSupportedException;
import java.time.DateTimeException;
import java.util.Iterator;
import java.util.List;

public class VistaTexto implements IVista {

    private static final String ERROR = "ERROR: No hay reservas.";
    private static final String NOMBRE_VALIDO = "Daniel";
    private static final String CORREO_VALIDO = "daniel@mail.com";
    private IControlador controlador;

    // Constructor que establece la vista
    public VistaTexto() {
        Opcion.setVista(this);
    }

    // Método que establece el controlador
    public void setControlador(IControlador controlador) {
        if(controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        } else {
            this.controlador = controlador;
        }
    }

    // Muestra la cabecera y el men� hasta que el usuario decide salir
    public void comenzar() {
        Consola.mostrarCabecera("Programa para la gestión de reservas de espacios del IES Al-Ándalus");
        int ordinalOpcion;

        do
        {
            Consola.mostrarMenu();
            ordinalOpcion = Consola.elegirOpcion();
            Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
            opcion.ejecutar();
        } while (ordinalOpcion != Opcion.SALIR.ordinal());
    }

    // M�todo para salir de la aplicaci�n
    public void salir() {
        Consola.mostrarCabecera("El programa se ha cerrado.");
        controlador.terminar();
    }

    // Inserta un aula y en caso afirmativo nos informa del resultado de la operaci�n
    public void insertarAula() {
        Consola.mostrarCabecera("Insertar un aula");

        try {
            controlador.insertarAula(Consola.leerAula());
            System.out.println("Se ha insertado el aula.\n");
        } catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Elimina un aula y nos informa del resultado de la operaci�n
    public void borrarAula() {
        Consola.mostrarCabecera("Eliminar un aula");

        try {
            controlador.borrarAula(Consola.leerAulaFicticia());
            System.out.println("Se ha eliminado el aula.\n");
        } catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Busca un aula y la muestra por consola
    public void buscarAula() {
        Aula aula;
        Consola.mostrarCabecera("Buscar un aula");

        try {
            aula = controlador.buscarAula(Consola.leerAulaFicticia());
            System.out.println(aula.toString());
        } catch(IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // Muestra el listado de las aulas por consola
    public void listarAulas() {
        List<String> aulas = controlador.representarAulas();
        Iterator<String> it = aulas.iterator();
        Consola.mostrarCabecera("Listado de aulas");

        if(aulas.size() == 0) {
            System.out.println("No hay aulas reservadas.\n");
        } else {
            while(it.hasNext()) {
                System.out.println(it.next().toString());
            }
        }
    }

    // Inserta un profesor y en caso afirmativo nos informa del resultado de la operaci�n
    public void insertarProfesor() {
        Consola.mostrarCabecera("Insertar un profesor");

        try {
            controlador.insertarProfesor(Consola.leerProfesor());
            System.out.println("Se ha insertado el profesor.\n");
        } catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Elimina un profesor y en caso afirmativo nos informa del resultado de la operaci�n
    public void borrarProfesor() {
        Consola.mostrarCabecera("Eliminar un profesor");

        try {
            controlador.borrarProfesor(Consola.leerProfesorFicticio());
            System.out.println("Se ha eliminado el profesor.\n");
        } catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Busca un profesor y lo muestra por consola
    public void buscarProfesor() {
        Profesor profesor;
        Consola.mostrarCabecera("Buscar un profesor");

        try {
            profesor = controlador.buscarProfesor(Consola.leerProfesorFicticio());
            System.out.println(profesor.toString());
        } catch(NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Muestra el listado de los profesores por consola
    public void listarProfesores() {
        List<String> profesores = controlador.representarProfesores();
        Iterator<String> it = profesores.iterator();
        Consola.mostrarCabecera("Listado de profesores");

        if(profesores.size() == 0) {
            System.out.println("No hay profesores.\n");
        } else {
            while(it.hasNext()) {
                System.out.println(it.next().toString());
            }
        }
    }

    // Permite insertar una reserva en nuestro ArrayList de reservas
    public void realizarReserva() {
        Consola.mostrarCabecera("Realizar una reserva");

        try {
            controlador.realizarReserva(Consola.leerReserva());
            System.out.println("Se ha realizado la reserva.\n");
        } catch(OperationNotSupportedException | NullPointerException | IllegalArgumentException | DateTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    // Permite anular una reserva
    public void anularReserva() {
        Consola.mostrarCabecera("Anular una reserva");

        try {
            controlador.anularReserva(Consola.leerReservaFicticia());
            System.out.println("Se ha anulado la reserva.\n");
        } catch(OperationNotSupportedException | NullPointerException | IllegalArgumentException | DateTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    // Lista las reservas que tenemos en el ArrayList por consola
    public void listarReservas() {
        List<String> reservas = controlador.representarReservas();
        Iterator<String> it = reservas.iterator();
        Consola.mostrarCabecera("Listado de reservas");

        if(reservas.size() == 0) {
            System.out.println("No hay reservas.\n");
        } else {
            while(it.hasNext()) {
                System.out.println(it.next().toString());
            }
        }
    }

    // Lista las reservas por aula que tenemos en el ArrayList por consola
    public void listarReservasAula() {
        List<Reserva> reservas = controlador.getReservasAula(Consola.leerAulaFicticia());
        Iterator<Reserva> it = reservas.iterator();
        Consola.mostrarCabecera("Listado de reservas por aula");

        if(reservas.size() == 0) {
            System.out.println("No hay aulas reservadas.\n");
        } else {
            while(it.hasNext()) {
                System.out.println(it.next().toString());
            }
        }
    }

    // Lista las reservas por profesor que tenemos en el ArrayList por consola
    public void listarReservasProfesor() {
        List<Reserva> reservas = controlador.getReservasProfesor(Consola.leerProfesorFicticio());
        Iterator<Reserva> it = reservas.iterator();
        Consola.mostrarCabecera("Listado de reservas por profesor");

        if(reservas.size() == 0) {
            System.out.println("No hay aulas reservadas.\n");
        } else {
            while(it.hasNext()) {
                System.out.println(it.next().toString());
            }
        }
    }

    // Muestra la disponibilidad de un aula por consola
    public void consultarDisponibilidad() {
        Consola.mostrarCabecera("Disponibilidad de las reservas");

        if(controlador.consultarDisponibilidad(Consola.leerAulaFicticia(), Consola.leerPermanencia())) {
            System.out.println("El aula consultada se encuentra disponible.\n");
        } else {
            System.out.println("El aula consultada no se encuentra disponible.\n");
        }
    }
}