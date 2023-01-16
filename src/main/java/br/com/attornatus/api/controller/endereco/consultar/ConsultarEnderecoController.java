package br.com.attornatus.api.controller.endereco.consultar;

import br.com.attornatus.api.model.PessoaEnderecoModel;
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
public class ConsultarEnderecoController implements ConsultarEnderecoControllerOpenApi {

  private final EnderecoService enderecoService;

  @Override
  @GetMapping("/{id}/enderecos")
  public PessoaEnderecoModel consultarPessoaComEnderecos(@PathVariable Long id) {
    return enderecoService.consultarPessoaComEnderecos(id);
  }
}
