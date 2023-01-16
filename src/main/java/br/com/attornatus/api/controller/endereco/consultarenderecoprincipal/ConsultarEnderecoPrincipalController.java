package br.com.attornatus.api.controller.endereco.consultarenderecoprincipal;

import br.com.attornatus.api.model.PessoaEnderecoPrincipalModel;
import br.com.attornatus.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ConsultarEnderecoPrincipalController
    implements ConsultarEnderecoPrincipalControllerOpenApi {

  private final EnderecoService enderecoService;

  @Override
  @GetMapping("/{id}/enderecos/principal")
  public PessoaEnderecoPrincipalModel consultarPrincipal(@PathVariable Long id) {
    return enderecoService.consultarEnderecoPrincipal(id);
  }
}
