package br.com.attornatus.api.controller.pessoa.criar;

import br.com.attornatus.api.input.PessoaInput;
import br.com.attornatus.api.model.PessoaModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pessoas")
public interface CriarPessoaControllerOpenApi {

  @Operation(
      summary = "Cria uma pessoa",
      responses = {@ApiResponse(responseCode = "201")})
  PessoaModel criar(
      @RequestBody(description = "Representação de uma nova pessoa", required = true)
          PessoaInput pessoaInput);
}
