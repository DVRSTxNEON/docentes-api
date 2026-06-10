package com.universidad.docentes;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Gestión de Docentes")
                        .version("1.0.0")
                        .description("""
                                API REST para el registro, consulta, actualización y eliminación \
                                de docentes universitarios.
                                
                                **Reglas de negocio:**
                                - No se puede registrar dos docentes con el mismo tipo y número de documento.
                                - Para actualizar o eliminar, el docente debe existir por su ID.
                                """)
                        .contact(new Contact()
                                .name("Universidad")
                                .email("sistemas@universidad.edu.co"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
