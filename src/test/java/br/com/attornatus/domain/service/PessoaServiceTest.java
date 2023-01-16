package br.com.attornatus.domain.service;

import br.com.attornatus.api.input.EnderecoInput;
import br.com.attornatus.api.input.PessoaInput;
import br.com.attornatus.api.mapper.EnderecoMapper;
import br.com.attornatus.api.mapper.PessoaMapper;
import br.com.attornatus.api.model.PessoaModel;
import br.com.attornatus.domain.entity.Pessoa;
import br.com.attornatus.domain.exception.ArgumentosInvalidosException;
import br.com.attornatus.domain.exception.EnderecoPrincipalJaExistenteException;
import br.com.attornatus.domain.exception.PessoaNaoEncontradaException;
import br.com.attornatus.domain.repository.PessoaRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.attornatus.dadosteste.EnderecoDadosTeste.enderecoInput;
import static br.com.attornatus.dadosteste.EnderecoDadosTeste.enderecoPrincipal;
import static br.com.attornatus.dadosteste.PessoaDadosTeste.criarLista;
import static br.com.attornatus.dadosteste.PessoaDadosTeste.pessoa;
import static br.com.attornatus.dadosteste.PessoaDadosTeste.pessoaInput;
import static br.com.attornatus.dadosteste.PessoaDadosTeste.pessoaInputSemDados;
import static br.com.attornatus.dadosteste.PessoaDadosTeste.pessoaModel;
import static br.com.attornatus.dadosteste.PessoaDadosTeste.pessoaSemEndereco;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class PessoaServiceTest {

  @Spy EnderecoMapper enderecoMapper = EnderecoMapper.INSTANCE;
  @Mock PessoaRepository pessoaRepository;
  @InjectMocks PessoaService pessoaService;
  @InjectMocks EnderecoService enderecoService;
  private Pessoa pessoa;
  private Pessoa pessoaSemEndereco;
  private List<Pessoa> listaDePessoas;
  private PessoaModel pessoaModel;
  private PessoaInput pessoaInput;
  private EnderecoInput enderecoInput;
  @Spy private PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;

  @BeforeEach
  void beforeEach() {
    pessoa = pessoa();
    pessoaInput = pessoaInput();
    pessoaModel = pessoaModel();
    pessoaSemEndereco = pessoaSemEndereco();
    enderecoInput = enderecoInput();
    listaDePessoas = criarLista();
  }

  @Test
  void deveRetornarId_QuandoCriarPessoa() {
    when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
    PessoaModel pessoaCriada = pessoaService.criar(pessoaInput);
    assertEquals(1L, pessoaCriada.getId());
    assertThat(pessoaCriada.getId()).isNotNull();
  }

  @Test
  void deveRetornarPessoa_QuandoConsultar() {
    when(pessoaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(pessoaSemEndereco));

    PessoaModel result = pessoaService.consultar(eq(1L));
    assertThat(result).isNotNull();
  }

  @Test
  void deveAlterarPessoa_QuandoEditar() {
    when(pessoaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(pessoa));
    pessoaModel.setNome("Marcos");
    pessoaModel.setDataNascimento(LocalDate.of(1970, 6, 26));
    pessoaService.editar(eq(1L), pessoaInput);
    pessoa = pessoaMapper.toEntity(pessoaInput);
    verify(pessoaRepository, atLeast(1)).save(pessoa);
  }

  @Test
  void deveRetornarListaCompleta() {
    when(pessoaRepository.findAll()).thenReturn(listaDePessoas);
    var list = pessoaService.listar();

    MatcherAssert.assertThat(list, Matchers.not(Matchers.empty()));
    MatcherAssert.assertThat(list, Matchers.hasSize(3));
    assertEquals(list.get(0).getClass(), PessoaModel.class);
    assertEquals("Maria Aparecida", list.get(0).getNome());
  }

  @Test
  void deveRetornarListaVazia_QuandoNaoEncontrarRegistro() {
    Mockito.when(pessoaRepository.findAll()).thenReturn(new ArrayList<>());

    var result = pessoaService.listar();

    MatcherAssert.assertThat(result, Matchers.notNullValue());
    MatcherAssert.assertThat(result, Matchers.empty());
  }

  @Test
  void deveRetornarExcecao_QuandoPessoaEmBranco() {
    var input = pessoaInputSemDados();

    var ex = assertThrows(ArgumentosInvalidosException.class, () -> pessoaService.criar(input));
    assertThat(ex)
        .hasMessage(
            "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");

    verify(this.pessoaRepository, times(0)).save(any());
  }

  @Test
  void deveRetornarExcecao_QuandoConsultarPessoaInexistente() {
    var ex =
        Assertions.catchThrowableOfType(
            () -> pessoaService.consultar(1000L), PessoaNaoEncontradaException.class);

    assertThat(ex).hasMessage("Pessoa com id 1000 não foi encontrada!");
  }

  @Test
  void deveRetornarExcecao_QuandoCadastrarSegundoEnderecoPrincipal() {
    pessoa.adicionarEndereco(enderecoPrincipal());

    when(pessoaRepository.findById(1L)).thenReturn(Optional.ofNullable(pessoa));

    var ex =
        Assertions.catchThrowableOfType(
            () -> enderecoService.criarEnderecoParaPessoa(1L, enderecoInput),
            EnderecoPrincipalJaExistenteException.class);

    assertThat(ex)
        .hasMessage(
            "Já existe um endereço principal cadastrado! "
                + "Por favor altere o campo 'principal' para 'false' do endereço 1 da pessoa com id 1, "
                + "e tente novamente!");
  }
}
