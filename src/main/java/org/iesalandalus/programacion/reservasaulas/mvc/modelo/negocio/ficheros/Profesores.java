package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {

    private static final String NOMBRE_FICHERO_PROFESORES = "datos/FichProfesores.dat";
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

    @Override
    public void comenzar() {
        leer();
    }

    private void leer() {
        File archivo = new File(NOMBRE_FICHERO_PROFESORES);
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {

            Profesor profesor = null;

            do {
                profesor = (Profesor) entrada.readObject();
                insertar(profesor);
            } while(profesor != null);
        } catch(FileNotFoundException e) {
            System.out.println("ERROR: Fichero no encontrado.");
        } catch(EOFException e) {
            System.out.println("Fichero leído correctamente.");
        } catch(IOException e) {
            System.out.println("Error de I/O.");
        } catch(ClassNotFoundException e) {
            System.out.println("ERROR: No se ha encontrado la clase.");
        } catch (OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public void terminar() {
        escribir();
    }

    private void escribir() {
        File archivo = new File(NOMBRE_FICHERO_PROFESORES);

        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
            for (Profesor profesor : coleccionProfesores) {
                salida.writeObject(profesor);
            }
            System.out.println("Fichero de profesores actualizado");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: No se pudo crear el fichero.");
        } catch (IOException e) {
            System.out.println("Error de I/O.");
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