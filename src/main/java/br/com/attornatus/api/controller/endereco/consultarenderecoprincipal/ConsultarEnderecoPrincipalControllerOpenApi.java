package br.com.attornatus.api.controller.endereco.consultarenderecoprincipal;

import br.com.attornatus.api.model.PessoaEnderecoPrincipalModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endereços")
public interface ConsultarEnderecoPrincipalControllerOpenApi {

  @Operation(
      summary = "Consulta uma pessoa com o seu endereço principal",
      responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(
            responseCode = "400",
            description = "ID da pessoa inválido",
            content = @Content(schema = @Schema(ref = "Problema"))),
        @ApiResponse(
            responseCode = "404",
            description = "Pessoa não encontrada",
            content = @Content(schema = @Schema(ref = "Problema")))
      })
  PessoaEnderecoPrincipalModel consultarPrincipal(
      @Parameter(description = "ID de uma pessoa", example = "1", required = true) Long id);
}
