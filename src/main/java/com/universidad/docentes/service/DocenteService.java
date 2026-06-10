package com.universidad.docentes.service;

import com.universidad.docentes.dto.DocenteDTO;
import com.universidad.docentes.exception.DocumentoDuplicadoException;
import com.universidad.docentes.exception.DocenteNotFoundException;
import com.universidad.docentes.model.Docente;
import com.universidad.docentes.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DocenteService {

    private final DocenteRepository docenteRepository;

    // =========================================================
    //  CREAR
    // =========================================================
    public DocenteDTO crearDocente(DocenteDTO dto) {
        // Regla de negocio: no duplicar tipoDocumento + numeroDocumento
        if (docenteRepository.existsByTipoDocumentoAndNumeroDocumento(
                dto.getTipoDocumento(), dto.getNumeroDocumento())) {
            throw new DocumentoDuplicadoException(dto.getTipoDocumento(), dto.getNumeroDocumento());
        }

        Docente docente = toEntity(dto);
        Docente guardado = docenteRepository.save(docente);
        return toDTO(guardado);
    }

    // =========================================================
    //  OBTENER TODOS
    // =========================================================
    @Transactional(readOnly = true)
    public List<DocenteDTO> obtenerTodos() {
        return docenteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // =========================================================
    //  OBTENER POR ID
    // =========================================================
    @Transactional(readOnly = true)
    public DocenteDTO obtenerPorId(Long id) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new DocenteNotFoundException(id));
        return toDTO(docente);
    }

    // =========================================================
    //  ACTUALIZAR
    // =========================================================
    public DocenteDTO actualizarDocente(Long id, DocenteDTO dto) {
        // Regla de negocio: verificar que exista por ID
        Docente existente = docenteRepository.findById(id)
                .orElseThrow(() -> new DocenteNotFoundException(id));

        // Si cambió el documento, verificar que no esté en uso por otro docente
        boolean documentoCambio =
                !existente.getTipoDocumento().equals(dto.getTipoDocumento()) ||
                !existente.getNumeroDocumento().equals(dto.getNumeroDocumento());

        if (documentoCambio &&
            docenteRepository.existsByTipoDocumentoAndNumeroDocumento(
                    dto.getTipoDocumento(), dto.getNumeroDocumento())) {
            throw new DocumentoDuplicadoException(dto.getTipoDocumento(), dto.getNumeroDocumento());
        }

        existente.setNumeroDocumento(dto.getNumeroDocumento());
        existente.setTipoDocumento(dto.getTipoDocumento());
        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setCorreo(dto.getCorreo());
        existente.setEspecialidad(dto.getEspecialidad());
        existente.setAniosExperiencia(dto.getAniosExperiencia());

        return toDTO(docenteRepository.save(existente));
    }

    // =========================================================
    //  ELIMINAR
    // =========================================================
    public void eliminarDocente(Long id) {
        // Regla de negocio: verificar que exista por ID
        if (!docenteRepository.existsById(id)) {
            throw new DocenteNotFoundException(id);
        }
        docenteRepository.deleteById(id);
    }

    // =========================================================
    //  MAPPERS
    // =========================================================
    private Docente toEntity(DocenteDTO dto) {
        return Docente.builder()
                .numeroDocumento(dto.getNumeroDocumento())
                .tipoDocumento(dto.getTipoDocumento())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .correo(dto.getCorreo())
                .especialidad(dto.getEspecialidad())
                .aniosExperiencia(dto.getAniosExperiencia())
                .build();
    }

    private DocenteDTO toDTO(Docente docente) {
        return DocenteDTO.builder()
                .id(docente.getId())
                .numeroDocumento(docente.getNumeroDocumento())
                .tipoDocumento(docente.getTipoDocumento())
                .nombre(docente.getNombre())
                .apellido(docente.getApellido())
                .correo(docente.getCorreo())
                .especialidad(docente.getEspecialidad())
                .aniosExperiencia(docente.getAniosExperiencia())
                .build();
    }
}
