package com.universidad.docentes.exception;

public class DocenteNotFoundException extends RuntimeException {

    public DocenteNotFoundException(Long id) {
        super("No se encontró ningún docente con el ID: " + id);
    }
}
