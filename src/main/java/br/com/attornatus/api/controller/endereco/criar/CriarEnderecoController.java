package br.com.attornatus.api.controller.endereco.criar;

import br.com.attornatus.api.input.EnderecoInput;
import br.com.attornatus.api.model.PessoaEnderecoModel;
import br.com.attornatus.domain.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CriarEnderecoController implements CriarEnderecoControllerOpenApi {

  private final EnderecoService enderecoService;

  @Override
  @PostMapping("/{id}/enderecos")
  @ResponseStatus(HttpStatus.CREATED)
  public PessoaEnderecoModel criarEnderecoParaPessoa(
      @PathVariable Long id, @RequestBody @Valid EnderecoInput endereco) {
    return enderecoService.criarEnderecoParaPessoa(id, endereco);
  }
}
