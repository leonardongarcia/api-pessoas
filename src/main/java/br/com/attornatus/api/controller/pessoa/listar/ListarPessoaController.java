package br.com.attornatus.api.controller.pessoa.listar;

import br.com.attornatus.api.model.PessoaModel;
import br.com.attornatus.domain.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/pessoas")
@RequiredArgsConstructor
public class ListarPessoaController implements ListarPessoaControllerOpenApi {

  private final PessoaService pessoaService;

  @Override
  @GetMapping
  public List<PessoaModel> listar() {
    return pessoaService.listar();
  }
}
