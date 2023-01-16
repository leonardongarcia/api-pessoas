package br.com.attornatus.api.controller.pessoa.consultar;

import br.com.attornatus.api.model.PessoaModel;
import br.com.attornatus.domain.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pessoas")
@RequiredArgsConstructor
public class ConsultarPessoaController implements ConsultarPessoaControllerOpenApi {

  private final PessoaService pessoaService;

  @Override
  @GetMapping("/{id}")
  public PessoaModel consultar(@PathVariable Long id) {
    return pessoaService.consultar(id);
  }
}
