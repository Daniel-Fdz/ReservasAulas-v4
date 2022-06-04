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

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas {

    private static final String NOMBRE_FICHERO_AULAS = "datos/FichAulas.dat";
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

    @Override
    public void comenzar() {
        leer();
    }

    private void leer() {
        File archivo = new File(NOMBRE_FICHERO_AULAS);
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
            Aula aula = null;

            do {
                aula = (Aula) entrada.readObject();
                insertar(aula);
            } while(aula != null);
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
        File archivo = new File(NOMBRE_FICHERO_AULAS);

        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
            for (Aula aula : coleccionAulas) {
                salida.writeObject(aula);
            }
            System.out.println("Fichero de aulas actualizado");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: No se pudo crear el fichero.");
        } catch (IOException e) {
            System.out.println("Error de I/O.");
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

    // M�todo que devuelve una copia profunda de las aulas
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