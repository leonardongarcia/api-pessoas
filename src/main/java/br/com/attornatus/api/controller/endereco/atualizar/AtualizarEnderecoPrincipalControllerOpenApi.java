package br.com.attornatus.api.controller.endereco.atualizar;

import br.com.attornatus.api.input.EnderecoPrincipalInput;
import br.com.attornatus.api.model.PessoaEnderecoPrincipalModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endereços")
public interface AtualizarEnderecoPrincipalControllerOpenApi {

  @Operation(
      summary = "Altera o endereço principal de uma pessoa",
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
  PessoaEnderecoPrincipalModel atualizarEnderecoPrincipal(
      @Parameter(description = "ID de uma pessoa", example = "1", required = true) Long pessoaId,
      @RequestBody(description = "Representação de um endereço", required = true)
          EnderecoPrincipalInput endereco);
}
