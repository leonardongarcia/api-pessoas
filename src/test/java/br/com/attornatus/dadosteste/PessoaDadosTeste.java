package br.com.attornatus.dadosteste;

import br.com.attornatus.api.input.PessoaInput;
import br.com.attornatus.api.model.PessoaEnderecoModel;
import br.com.attornatus.api.model.PessoaModel;
import br.com.attornatus.domain.entity.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.attornatus.dadosteste.EnderecoDadosTeste.enderecoModel;
import static br.com.attornatus.dadosteste.EnderecoDadosTeste.enderecos;

@Builder
@AllArgsConstructor
public class PessoaDadosTeste {

  public static Pessoa pessoa() {
    return Pessoa.builder()
        .id(1L)
        .nome("Leonardo")
        .dataNascimento(LocalDate.of(1989, 3, 19))
        .enderecos(enderecos())
        .build();
  }

  public static Pessoa pessoaSemEndereco() {
    var pessoaSemEndereco = pessoa();
    pessoaSemEndereco.setEnderecos(null);
    return pessoaSemEndereco;
  }

  public static Pessoa pessoaComId() {
    return Pessoa.builder().id(1L).build();
  }

  public static PessoaInput pessoaInput() {
    return PessoaInput.builder().nome("Maria das dores").dataNascimento("23/03/1989").build();
  }

  public static PessoaInput pessoaInputSemDados() {
    return PessoaInput.builder().build();
  }

  public static PessoaModel pessoaModel() {
    return PessoaModel.builder()
        .id(1L)
        .nome("Leonardo")
        .dataNascimento(LocalDate.of(1989, 3, 19))
        .build();
  }

  public static PessoaEnderecoModel pessoaEnderecoModel() {
    return PessoaEnderecoModel.builder()
        .id(1L)
        .nome("Leonardo")
        .dataNascimento(LocalDate.of(1989, 3, 19))
        .enderecos(List.of(enderecoModel()))
        .build();
  }

  public static List<Pessoa> criarLista() {
    return new ArrayList<>(
        List.of(
            Pessoa.builder()
                .id(1L)
                .nome("Maria Aparecida")
                .dataNascimento(LocalDate.now().minusYears(32))
                .build(),
            Pessoa.builder()
                .id(1L)
                .nome("José da Silva")
                .dataNascimento(LocalDate.now().minusYears(32))
                .build(),
            Pessoa.builder()
                .id(1L)
                .nome("Emanuel de Sousa")
                .dataNascimento(LocalDate.now().minusYears(32))
                .build()));
  }
}
