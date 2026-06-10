package com.universidad.docentes.exception;

import com.universidad.docentes.model.TipoDocumento;

public class DocumentoDuplicadoException extends RuntimeException {

    public DocumentoDuplicadoException(TipoDocumento tipo, String numero) {
        super("Ya existe un docente con " + tipo + " N° " + numero);
    }
}
