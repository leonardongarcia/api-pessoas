package br.com.attornatus.api.controller.pessoa.editar;

import br.com.attornatus.api.input.PessoaInput;
import br.com.attornatus.api.model.PessoaModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pessoas")
public interface EditarPessoaControllerOpenApi {

  @Operation(
      summary = "Edita uma pessoa",
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
  PessoaModel editar(
      @Parameter(description = "ID de uma pessoa", example = "1", required = true) Long id,
      @RequestBody(description = "Representação de uma pessoa", required = true) PessoaInput input);
}
