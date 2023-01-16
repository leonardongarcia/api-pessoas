package br.com.attornatus.api.controller.endereco.consultar;

import br.com.attornatus.api.model.PessoaEnderecoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endereços")
public interface ConsultarEnderecoControllerOpenApi {

  @Operation(
      summary = "Consulta uma pessoa com todos os endereços",
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
  PessoaEnderecoModel consultarPessoaComEnderecos(
      @Parameter(description = "ID de uma cidade", example = "1", required = true) Long id);
}
