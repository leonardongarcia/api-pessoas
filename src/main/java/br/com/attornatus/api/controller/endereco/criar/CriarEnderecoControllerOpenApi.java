package br.com.attornatus.api.controller.endereco.criar;

import br.com.attornatus.api.input.EnderecoInput;
import br.com.attornatus.api.model.PessoaEnderecoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endereços")
public interface CriarEnderecoControllerOpenApi {

  @Operation(
      summary = "Cria um endereço para uma pessoa",
      description = "É permitido ter apenas um endereço principal",
      responses = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(
            responseCode = "422",
            description = "Já há um endereço principal cadastrado",
            content = @Content(schema = @Schema(ref = "Problema")))
      })
  PessoaEnderecoModel criarEnderecoParaPessoa(
      @Parameter(description = "ID de uma pessoa", example = "1", required = true) Long id,
      @RequestBody(description = "Representação de um endereço", required = true)
          EnderecoInput endereco);
}
