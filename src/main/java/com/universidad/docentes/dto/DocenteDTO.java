package com.universidad.docentes.dto;

import com.universidad.docentes.model.TipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos del Docente")
public class DocenteDTO {

    @Schema(description = "ID único del docente (solo en respuestas)", example = "1")
    private Long id;

    @NotBlank(message = "El número de documento es obligatorio")
    @Schema(description = "Número del documento de identidad", example = "12345678")
    private String numeroDocumento;

    @NotNull(message = "El tipo de documento es obligatorio")
    @Schema(description = "Tipo de documento: CC, CE, TI, PP, NIT", example = "CC")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    @Schema(description = "Nombre del docente", example = "Carlos")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100)
    @Schema(description = "Apellido del docente", example = "Pérez")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Schema(description = "Correo electrónico institucional", example = "cperez@universidad.edu.co")
    private String correo;

    @NotBlank(message = "La especialidad es obligatoria")
    @Schema(description = "Área de especialidad del docente", example = "Ingeniería de Software")
    private String especialidad;

    @Min(0) @Max(60)
    @Schema(description = "Años de experiencia docente", example = "10")
    private Integer aniosExperiencia;
}
