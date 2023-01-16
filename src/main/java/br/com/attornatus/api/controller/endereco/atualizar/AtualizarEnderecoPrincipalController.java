package br.com.attornatus.api.controller.endereco.atualizar;

import br.com.attornatus.api.input.EnderecoPrincipalInput;
import br.com.attornatus.api.model.PessoaEnderecoPrincipalModel;
import br.com.attornatus.domain.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AtualizarEnderecoPrincipalController
    implements AtualizarEnderecoPrincipalControllerOpenApi {

  private final EnderecoService enderecoService;

  @Override
  @PutMapping("/{id}/enderecos/principal")
  public PessoaEnderecoPrincipalModel atualizarEnderecoPrincipal(
      @PathVariable Long id, @RequestBody @Valid EnderecoPrincipalInput endereco) {
    return enderecoService.atualizarEnderecoPrincipal(id, endereco);
  }
}
