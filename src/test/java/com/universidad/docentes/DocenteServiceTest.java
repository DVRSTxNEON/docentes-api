package com.universidad.docentes;

import com.universidad.docentes.dto.DocenteDTO;
import com.universidad.docentes.exception.DocumentoDuplicadoException;
import com.universidad.docentes.exception.DocenteNotFoundException;
import com.universidad.docentes.model.Docente;
import com.universidad.docentes.model.TipoDocumento;
import com.universidad.docentes.repository.DocenteRepository;
import com.universidad.docentes.service.DocenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocenteServiceTest {

    @Mock
    private DocenteRepository docenteRepository;

    @InjectMocks
    private DocenteService docenteService;

    private DocenteDTO dto;
    private Docente docente;

    @BeforeEach
    void setUp() {
        dto = DocenteDTO.builder()
                .numeroDocumento("12345678")
                .tipoDocumento(TipoDocumento.CC)
                .nombre("Carlos")
                .apellido("Pérez")
                .correo("cperez@test.com")
                .especialidad("Sistemas")
                .aniosExperiencia(5)
                .build();

        docente = Docente.builder()
                .id(1L)
                .numeroDocumento("12345678")
                .tipoDocumento(TipoDocumento.CC)
                .nombre("Carlos")
                .apellido("Pérez")
                .correo("cperez@test.com")
                .especialidad("Sistemas")
                .aniosExperiencia(5)
                .build();
    }

    @Test
    void crearDocente_exitoso() {
        when(docenteRepository.existsByTipoDocumentoAndNumeroDocumento(any(), any())).thenReturn(false);
        when(docenteRepository.save(any())).thenReturn(docente);

        DocenteDTO resultado = docenteService.crearDocente(dto);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        verify(docenteRepository, times(1)).save(any());
    }

    @Test
    void crearDocente_documentoDuplicado_lanzaExcepcion() {
        when(docenteRepository.existsByTipoDocumentoAndNumeroDocumento(any(), any())).thenReturn(true);

        assertThrows(DocumentoDuplicadoException.class, () -> docenteService.crearDocente(dto));
        verify(docenteRepository, never()).save(any());
    }

    @Test
    void eliminarDocente_noExiste_lanzaExcepcion() {
        when(docenteRepository.existsById(99L)).thenReturn(false);

        assertThrows(DocenteNotFoundException.class, () -> docenteService.eliminarDocente(99L));
    }

    @Test
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(docenteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DocenteNotFoundException.class, () -> docenteService.obtenerPorId(99L));
    }
}
