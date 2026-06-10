package com.universidad.docentes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(
    name = "docentes",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_tipo_numero_documento",
            columnNames = {"tipo_documento", "numero_documento"}
        )
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de documento es obligatorio")
    @Column(name = "numero_documento", nullable = false, length = 20)
    private String numeroDocumento;

    @NotNull(message = "El tipo de documento es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false, length = 10)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    @NotBlank(message = "La especialidad es obligatoria")
    @Column(name = "especialidad", nullable = false, length = 100)
    private String especialidad;

    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    @Max(value = 60, message = "Los años de experiencia no pueden superar 60")
    @Column(name = "anios_experiencia", nullable = false)
    private Integer aniosExperiencia;
}
