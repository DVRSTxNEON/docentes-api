package com.universidad.docentes.repository;

import com.universidad.docentes.model.Docente;
import com.universidad.docentes.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

    /**
     * Verifica si ya existe un docente con el mismo tipo y número de documento.
     * Regla de negocio: no se puede repetir la combinación tipoDocumento + numeroDocumento.
     */
    boolean existsByTipoDocumentoAndNumeroDocumento(
            TipoDocumento tipoDocumento, String numeroDocumento);

    /**
     * Busca un docente por tipo y número de documento.
     */
    Optional<Docente> findByTipoDocumentoAndNumeroDocumento(
            TipoDocumento tipoDocumento, String numeroDocumento);
}
