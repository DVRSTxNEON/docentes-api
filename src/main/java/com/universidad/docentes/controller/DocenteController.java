package com.universidad.docentes.controller;

import com.universidad.docentes.dto.DocenteDTO;
import com.universidad.docentes.service.DocenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docentes")
@RequiredArgsConstructor
@Tag(name = "Docentes", description = "API para la gestión de docentes universitarios")
public class DocenteController {

    private final DocenteService docenteService;

    // =========================================================
    //  POST /api/v1/docentes  ->  CREAR
    // =========================================================
    @Operation(
        summary = "Crear un nuevo docente",
        description = "Registra un docente. No se permite duplicar tipo + número de documento."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Docente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "Ya existe un docente con ese documento")
    })
    @PostMapping
    public ResponseEntity<DocenteDTO> crear(
            @Valid @RequestBody DocenteDTO dto) {
        DocenteDTO creado = docenteService.crearDocente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // =========================================================
    //  GET /api/v1/docentes  ->  LISTAR TODOS
    // =========================================================
    @Operation(summary = "Obtener todos los docentes", description = "Retorna la lista completa de docentes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<DocenteDTO>> obtenerTodos() {
        return ResponseEntity.ok(docenteService.obtenerTodos());
    }

    // =========================================================
    //  GET /api/v1/docentes/{id}  ->  OBTENER POR ID
    // =========================================================
    @Operation(summary = "Obtener docente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Docente encontrado"),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado",
            content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DocenteDTO> obtenerPorId(
            @Parameter(description = "ID del docente", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(docenteService.obtenerPorId(id));
    }

    // =========================================================
    //  PUT /api/v1/docentes/{id}  ->  ACTUALIZAR
    // =========================================================
    @Operation(
        summary = "Actualizar docente",
        description = "Actualiza un docente existente. Verifica que el ID exista previamente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Docente actualizado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado"),
        @ApiResponse(responseCode = "409", description = "Documento duplicado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DocenteDTO> actualizar(
            @Parameter(description = "ID del docente a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody DocenteDTO dto) {
        return ResponseEntity.ok(docenteService.actualizarDocente(id, dto));
    }

    // =========================================================
    //  DELETE /api/v1/docentes/{id}  ->  ELIMINAR
    // =========================================================
    @Operation(
        summary = "Eliminar docente",
        description = "Elimina un docente por ID. Verifica que el ID exista previamente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Docente eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del docente a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        docenteService.eliminarDocente(id);
        return ResponseEntity.noContent().build();
    }
}
