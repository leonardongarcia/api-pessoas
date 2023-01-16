package br.com.attornatus.domain.service;

import br.com.attornatus.api.input.EnderecoInput;
import br.com.attornatus.api.input.EnderecoPrincipalInput;
import br.com.attornatus.api.mapper.EnderecoMapper;
import br.com.attornatus.api.mapper.PessoaMapper;
import br.com.attornatus.api.model.PessoaEnderecoModel;
import br.com.attornatus.domain.entity.Endereco;
import br.com.attornatus.domain.entity.Pessoa;
import br.com.attornatus.domain.exception.EnderecoPrincipalNaoEncontradoException;
import br.com.attornatus.domain.repository.EnderecoRepository;
import br.com.attornatus.domain.repository.PessoaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.attornatus.dadosteste.EnderecoDadosTeste.enderecoAlterado;
import static br.com.attornatus.dadosteste.EnderecoDadosTeste.enderecoInput;
import static br.com.attornatus.dadosteste.EnderecoDadosTeste.enderecoPrincipal;
import static br.com.attornatus.dadosteste.EnderecoDadosTeste.enderecoPrincipalInput;
import static br.com.attornatus.dadosteste.PessoaDadosTeste.pessoa;
import static br.com.attornatus.dadosteste.PessoaDadosTeste.pessoaEnderecoModel;
import static br.com.attornatus.dadosteste.PessoaDadosTeste.pessoaSemEndereco;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class EnderecoServiceTest {

  Pessoa pessoa;
  Pessoa pessoaSemEndereco;
  PessoaEnderecoModel pessoaEnderecoModel;
  Endereco endereco;
  Endereco enderecoAlteardo;
  EnderecoInput enderecoInput;
  EnderecoPrincipalInput enderecoPrincipalInput;

  @Spy EnderecoMapper enderecoMapper = EnderecoMapper.INSTANCE;
  @Spy PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;
  @InjectMocks EnderecoService enderecoService;
  @Mock private EnderecoRepository enderecoRepository;
  @Mock private PessoaRepository pessoaRepository;

  @BeforeEach
  void setUp() {

    pessoa = pessoa();
    pessoaSemEndereco = pessoaSemEndereco();
    pessoaEnderecoModel = pessoaEnderecoModel();
    endereco = enderecoPrincipal();
    enderecoAlteardo = enderecoAlterado();
    enderecoInput = enderecoInput();
    enderecoPrincipalInput = enderecoPrincipalInput();

    when(pessoaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(pessoa));
  }

  @Test
  void deveRetornarPessoaComEnderecos() {

    var resultado = enderecoService.consultarPessoaComEnderecos(1L);

    assertThat(resultado).isNotNull();
    assertThat(resultado.getEnderecos()).isNotNull();
  }

  @Test
  void deveCriarEnderecoParaPessoa() {
    when(pessoaRepository.save(pessoaSemEndereco)).thenReturn(pessoa);

    var result = enderecoService.criarEnderecoParaPessoa(1L, enderecoInput);

    assertThat(result).isNotNull();
  }

  @Test
  void deveRetornarPessoaComEnderecoPrincipal() {
    pessoa.adicionarEndereco(enderecoPrincipal());

    var resultado = enderecoService.consultarEnderecoPrincipal(1L);

    assertThat(resultado).isNotNull();
    assertThat(resultado.getEndereco().getPrincipal()).isTrue();
  }

  @Test
  void deveAtualizarEnderecoPrincipal() {
    pessoa.adicionarEndereco(enderecoPrincipal());
    when(enderecoRepository.save(endereco)).thenReturn(enderecoAlteardo);

    var result = enderecoService.atualizarEnderecoPrincipal(1L, enderecoPrincipalInput);

    assertThat(result).isNotNull();
    assertThat(result.getEndereco().getPrincipal()).isFalse();
  }

  @Test
  void deveRetornarExcecao_QuandoPessoaSemEnderecoPrincipal() {

    var ex =
        Assertions.catchThrowableOfType(
            () -> enderecoService.atualizarEnderecoPrincipal(1L, enderecoPrincipalInput),
            EnderecoPrincipalNaoEncontradoException.class);

    assertThat(ex).hasMessage("Não há endereço principal cadastrado para a pessoa com id 1");
  }
}
