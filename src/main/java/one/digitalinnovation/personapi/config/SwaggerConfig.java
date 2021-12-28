package one.digitalinnovation.personapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springBlogPessoalOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto api pessoal")
                        .description("Projeto api pessoal - Digital innovation one")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Digital innovation one ")
                                .url("https://web.dio.me"))
                        .contact(new Contact()
                                .name("giovana borges")
                                .email("gigi12345as17@gmail.com")
                                .url("https://github.com/GiovanaBorges")));
    }

    @Bean
    public OpenApiCustomiser customerGlobalResponseStatus() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses api = operation.getResponses();

                api.addApiResponse("200", createApiResponse("Sucess!"));
                api.addApiResponse("201", createApiResponse("Created!"));
                api.addApiResponse("400", createApiResponse("Request error!"));
                api.addApiResponse("401", createApiResponse("Not authorized!"));
                api.addApiResponse("500", createApiResponse("Internal server Error!"));
            }));
        };
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}