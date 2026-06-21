# API REST - Gestión de Docentes

Proyecto desarrollado para la materia de desarrollo backend. Consiste en una API REST hecha en Spring Boot que permite crear, consultar, actualizar y eliminar docentes.

---

## Integrantes

- Jeam Pierre Morales Henao
- Lucia Capera

---

## ¿Qué necesita para ejecutarlo?

- Java 17 → https://adoptium.net
- Maven → https://maven.apache.org/download.cgi
- VS Code o IntelliJ

---

## ¿Cómo se ejecuta?

```bash
mvn spring-boot:run
```

Cuando en la consola aparezca `Started DocentesApiApplication` ya está funcionando.

---

## ¿Dónde lo prueba?

Abre el navegador y entra a:

```
http://localhost:8080/swagger-ui.html
```

Ahí puede probar todos los endpoints sin necesidad de Postman.

---

## Endpoints

| Método |           URL           |         ¿Qué hace?        |
|--------|-------------------------|---------------------------|
|  POST  |    `/api/v1/docentes`   |      Crea un docente      |
|   GET  |    `/api/v1/docentes`   |  Trae todos los docentes  |
|   GET  | `/api/v1/docentes/{id}` | Trae un docente por su ID |
|   PUT  | `/api/v1/docentes/{id}` |    Actualiza un docente   |
| DELETE | `/api/v1/docentes/{id}` |     Elimina un docente    |

---

## Reglas de negocio

- No se pueden registrar dos docentes con el mismo tipo y número de documento.
- Para actualizar o eliminar un docente, primero se verifica que exista por su ID.

---

## Atributos del Docente

- id
- numeroDocumento
- tipoDocumento (CC, CE, TI, PP, NIT)
- nombre
- apellido
- correo
- especialidad
- aniosExperiencia

---

## Tecnologías usadas

- Java 17
- Spring Boot 3
- H2 (base de datos en memoria)
- Swagger para documentación
- Maven