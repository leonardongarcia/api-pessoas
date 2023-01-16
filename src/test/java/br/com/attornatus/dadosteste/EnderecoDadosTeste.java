package br.com.attornatus.dadosteste;

import br.com.attornatus.api.input.EnderecoInput;
import br.com.attornatus.api.input.EnderecoPrincipalInput;
import br.com.attornatus.api.model.EnderecoModel;
import br.com.attornatus.domain.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import static br.com.attornatus.dadosteste.PessoaDadosTeste.pessoaComId;

@Builder
@AllArgsConstructor
public class EnderecoDadosTeste {

  public static List<Endereco> enderecos() {

    return new ArrayList<>(
        List.of(
            Endereco.builder()
                .id(1L)
                .logradouro("R. Miguel Inácio Faraco")
                .numero(355)
                .cidade("Tubarão")
                .cep("88705-050")
                .principal(false)
                .pessoa(pessoaComId())
                .build()));
  }

  public static Endereco enderecoPrincipal() {

    return Endereco.builder()
        .id(1L)
        .logradouro("R. Miguel Inácio Faraco")
        .numero(355)
        .cidade("Tubarão")
        .cep("88705-050")
        .principal(true)
        .pessoa(pessoaComId())
        .build();
  }

  public static Endereco enderecoAlterado() {
    var enderecoPrincipal = enderecoPrincipal();
    enderecoPrincipal.setPrincipal(false);
    return enderecoPrincipal;
  }

  public static EnderecoModel enderecoModel() {
    return EnderecoModel.builder()
        .id(1L)
        .logradouro("R. Miguel Inácio Faraco")
        .numero(355)
        .cidade("Tubarão")
        .cep("88705-050")
        .principal(true)
        .build();
  }

  public static EnderecoInput enderecoInput() {
    return EnderecoInput.builder()
        .logradouro("R. Miguel Inácio Faraco")
        .numero(355)
        .cidade("Tubarão")
        .cep("88705-050")
        .principal(true)
        .build();
  }

  public static EnderecoPrincipalInput enderecoPrincipalInput() {
    return EnderecoPrincipalInput.builder()
        .logradouro("R. Miguel Inácio Faraco")
        .numero(355)
        .cidade("Tubarão")
        .cep("88705-050")
        .principal(false)
        .build();
  }
}
