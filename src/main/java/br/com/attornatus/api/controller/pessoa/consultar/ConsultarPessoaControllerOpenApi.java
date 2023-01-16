package br.com.attornatus.api.controller.pessoa.consultar;

import br.com.attornatus.api.model.PessoaModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pessoas")
public interface ConsultarPessoaControllerOpenApi {

  @Operation(
      summary = "Consulta uma pessoa",
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
  PessoaModel consultar(
      @Parameter(description = "ID de uma cidade", example = "1", required = true) Long id);
}
