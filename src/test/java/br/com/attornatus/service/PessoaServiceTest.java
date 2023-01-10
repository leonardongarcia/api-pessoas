package br.com.attornatus.service;

import br.com.attornatus.entity.Endereco;
import br.com.attornatus.entity.Pessoa;
import br.com.attornatus.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

  private Pessoa pessoa;

  @Mock private PessoaRepository pessoaRepository;

  @InjectMocks private PessoaService pessoaService;

  @BeforeEach
  void beforeEach() {
    Endereco endereco =
        Endereco.builder()
            .logradouro("R. Miguel Inácio Faraco")
            .numero(355)
            .cidade("Tubarão")
            .cep("88705-050")
            .build();

    pessoa =
        Pessoa.builder()
            .nome("Mario")
            .dataNascimento(LocalDate.of(1990, 7, 27))
            .endereco(endereco)
            .build();
  }

  @Test
  void deveRetornarIdQuandoCriarPessoa() {
    Mockito.when(pessoaService.criar(pessoa))
        .thenAnswer(
            invocationOnMock -> {
              Pessoa pessoaEnviada = invocationOnMock.getArgument(0, Pessoa.class);
              pessoaEnviada.setId(1L);
              return pessoaEnviada;
            });

    Pessoa pessoaCriada = pessoaService.criar(pessoa);
    Assertions.assertEquals(1L, pessoaCriada.getId());
  }

  @Test
  void deveChamarRepositoryQuandoCriarPessoa() {
    pessoaService.criar(pessoa);
    Mockito.verify(pessoaRepository, Mockito.times(1)).save(pessoa);
  }
}
