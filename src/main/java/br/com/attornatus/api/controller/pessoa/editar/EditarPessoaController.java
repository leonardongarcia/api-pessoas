package br.com.attornatus.api.controller.pessoa.editar;

import br.com.attornatus.api.input.PessoaInput;
import br.com.attornatus.api.model.PessoaModel;
import br.com.attornatus.domain.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pessoas")
@RequiredArgsConstructor
public class EditarPessoaController implements EditarPessoaControllerOpenApi {

  private final PessoaService pessoaService;

  @Override
  @PutMapping("/{id}")
  public PessoaModel editar(@PathVariable Long id, @RequestBody @Valid PessoaInput input) {
    return pessoaService.editar(id, input);
  }
}
