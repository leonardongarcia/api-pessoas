package br.com.attornatus.api.controller.pessoa.criar;

import br.com.attornatus.api.input.PessoaInput;
import br.com.attornatus.api.model.PessoaModel;
import br.com.attornatus.domain.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pessoas")
@RequiredArgsConstructor
public class CriarPessoaController implements CriarPessoaControllerOpenApi {

  private final PessoaService pessoaService;

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PessoaModel criar(@RequestBody @Valid PessoaInput pessoaInput) {
    return pessoaService.criar(pessoaInput);
  }
}
