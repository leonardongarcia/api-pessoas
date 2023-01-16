package br.com.attornatus.config;

import br.com.attornatus.api.exceptionhandler.Problem;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class OpenApiConfig {

    public static final String BAD_REQUEST_RESPONSE = "BadRequestResponse";
    public static final String NOT_FOUND_RESPONSE = "NotFoundResponse";
    public static final String NOT_ACCEPTABLE_RESPONSE = "NotAcceptableResponse";
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "InternalServerErrorResponse";

  @Bean
  public OpenAPI springShopOpenApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Api de pessoas")
                .description("Api para gerenciamento de pessoas.")
                .version("V1")
                .license(new License().name("MIT License").url("https:ADICIONAR LICENSA")))
        .externalDocs(
            new ExternalDocumentation()
                .description("Attornatus Procuradoria Digital")
                .url("https://www.attornatus.com.br/"))
        .tags(Arrays.asList(
                new Tag().name("Pessoas").description("Gerencia as pessoas"),
                new Tag().name("Endereços").description("Gerencia os endereços")))
            .components(new Components()
                    .schemas(gerarSchemas())
                    .responses(gerarResponses()
                    ));
  }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi ->
                openApi
                        .getPaths()
                        .values()
                        .forEach(
                                pathItem ->
                                        pathItem
                                                .readOperationsMap()
                                                .forEach(
                                                        (httpMethod, operation) -> {
                                                            ApiResponses responses = operation.getResponses();
                                                            switch (httpMethod) {
                                                                case GET -> {
                                                                    responses.addApiResponse("406", new ApiResponse().$ref(NOT_ACCEPTABLE_RESPONSE));
                                                                    responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
                                                                }
                                                                case POST, PUT -> {
                                                                    responses.addApiResponse("400", new ApiResponse().$ref(BAD_REQUEST_RESPONSE));
                                                                    responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
                                                                }
                                                                default -> responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
                                                            }
                                                        }));
    }

    private Map<String, Schema> gerarSchemas(){
        final Map<String, Schema> schemaMap = new HashMap<>();

        var problemSchema = ModelConverters.getInstance().read(Problem.class);
        var problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(new Schema<Problem>().$ref("Problema")));

        apiResponseMap.put(BAD_REQUEST_RESPONSE, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(NOT_FOUND_RESPONSE, new ApiResponse()
                .description("Recurso não encontrado")
                .content(content));

        apiResponseMap.put(NOT_ACCEPTABLE_RESPONSE, new ApiResponse()
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .content(content));

        apiResponseMap.put(INTERNAL_SERVER_ERROR_RESPONSE, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content));

        return apiResponseMap;
    }
}