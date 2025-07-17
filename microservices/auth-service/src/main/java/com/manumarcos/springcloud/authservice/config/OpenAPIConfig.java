package com.manumarcos.springcloud.authservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI(){

        Contact contact = new Contact();
        contact.setName("👨🏻‍💻Backend Dev");
        contact.setEmail("manuelignaciomarcos@gmail.com");
        contact.setUrl("https://manumarcos.com.ar/");

        License license = new License();
        license.setName("📝Licence - MIT");
        license.setUrl("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Mind Battle - Auth Service API")
                .version("1.0.0")
                .description("Servicio responsable de la autenticación de usuarios en Mind Battle. Maneja el" +
                        " login, registro y emisión de tokens JWT.")
                .contact(contact)
                .license(license);

        return new OpenAPI().info(info);
    }
}
