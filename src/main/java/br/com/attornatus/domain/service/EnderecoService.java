package br.com.attornatus.domain.service;

import br.com.attornatus.api.input.EnderecoInput;
import br.com.attornatus.api.input.EnderecoPrincipalInput;
import br.com.attornatus.api.mapper.EnderecoMapper;
import br.com.attornatus.api.mapper.PessoaMapper;
import br.com.attornatus.api.model.PessoaEnderecoModel;
import br.com.attornatus.api.model.PessoaEnderecoPrincipalModel;
import br.com.attornatus.domain.entity.Endereco;
import br.com.attornatus.domain.entity.Pessoa;
import br.com.attornatus.domain.exception.EnderecoPrincipalJaExistenteException;
import br.com.attornatus.domain.exception.EnderecoPrincipalNaoEncontradoException;
import br.com.attornatus.domain.exception.PessoaNaoEncontradaException;
import br.com.attornatus.domain.repository.EnderecoRepository;
import br.com.attornatus.domain.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class EnderecoService {

  private static final PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;
  private static final EnderecoMapper enderecoMapper = EnderecoMapper.INSTANCE;
  private final PessoaRepository pessoaRepository;
  private final EnderecoRepository enderecoRepository;

  public PessoaEnderecoModel consultarPessoaComEnderecos(Long pessoaId) {
    return pessoaMapper.toEnderecoModel(buscarOuFalhar(pessoaId));
  }

  public PessoaEnderecoPrincipalModel consultarEnderecoPrincipal(Long pessoaId) {
    Pessoa pessoa = buscarOuFalhar(pessoaId);

    Endereco enderecoPrincipal = getEnderecoPrincipal(pessoa);
    return pessoaMapper.toPrincipal(pessoa, enderecoPrincipal);
  }

  @Transactional
  @Modifying
  public PessoaEnderecoModel criarEnderecoParaPessoa(Long pessoaId, EnderecoInput enderecoInput) {
    Pessoa pessoa = buscarOuFalhar(pessoaId);
    var endereco = getEndereco(enderecoInput);

    Endereco enderecoPrincipal = getEnderecoPrincipal(pessoa);

    jaPossuiEnderecoPrincipal(endereco, enderecoPrincipal);

    pessoa.adicionarEndereco(endereco);
    endereco.setPessoa(pessoa);

    var enderecoSalvo = enderecoRepository.save(endereco);
    return pessoaMapper.toModel(pessoaRepository.save(pessoa), enderecoSalvo);
  }

  @Transactional
  @Modifying
  public PessoaEnderecoPrincipalModel atualizarEnderecoPrincipal(
      Long pessoaId, EnderecoPrincipalInput enderecoInput) {

    PessoaEnderecoPrincipalModel pessoaEnderecoPrincipalModel =
        consultarEnderecoPrincipal(pessoaId);

    if (pessoaEnderecoPrincipalModel.getEndereco() == null) {
      throw new EnderecoPrincipalNaoEncontradoException(pessoaId);
    }

    Pessoa pessoa = pessoaMapper.principaltoEntity(pessoaEnderecoPrincipalModel);
    var enderecoAtual = enderecoMapper.toEntity(pessoaEnderecoPrincipalModel.getEndereco());
    var enderecoASalvar = enderecoMapper.principalToEntity(enderecoAtual, enderecoInput);
    enderecoASalvar.setPessoa(pessoa);
    Endereco enderecoSalvo = enderecoRepository.save(enderecoASalvar);

    pessoaEnderecoPrincipalModel.setEndereco(enderecoMapper.toModel(enderecoSalvo));
    return pessoaMapper.toPrincipal(pessoa, enderecoSalvo);
  }

  private Pessoa buscarOuFalhar(Long pessoaId) {
    return pessoaRepository
        .findById(pessoaId)
        .orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
  }

  private Endereco getEndereco(EnderecoInput enderecoInput) {
    return enderecoMapper.toEntity(enderecoInput);
  }

  private Endereco getEnderecoPrincipal(Pessoa pessoa) {
    return pessoa.getEnderecos().stream().filter(Endereco::getPrincipal).findFirst().orElse(null);
  }

  private void jaPossuiEnderecoPrincipal(Endereco endereco, Endereco enderecoPrincipal) {
    if (endereco.getPrincipal() == Boolean.TRUE && (nonNull(enderecoPrincipal))) {
      throw new EnderecoPrincipalJaExistenteException(
          enderecoPrincipal.getId(), enderecoPrincipal.getPessoa().getId());
    }
  }
}
