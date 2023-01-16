package br.com.attornatus.api.controller.pessoa.listar;

import br.com.attornatus.api.model.PessoaModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Pessoas")
public interface ListarPessoaControllerOpenApi {

  @Operation(summary = "Lista as pessoas")
  List<PessoaModel> listar();
}
