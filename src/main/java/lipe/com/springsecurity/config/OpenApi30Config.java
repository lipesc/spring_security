package lipe.com.springsecurity.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "lipesc API", version = "v1", description = """
    gerenciador de tarefas simples, com registro de usuario e validação com jwt para demonstrar conceitos de backend:

CRUD (Create, Read, Update, Delete) de tarefas
Autenticação JWT com Spring Security
Persistência de dados com Spring Data JPA e MySQL
    """))
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApi30Config {

}
