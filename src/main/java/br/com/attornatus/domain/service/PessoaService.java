package br.com.attornatus.domain.service;

import br.com.attornatus.api.input.PessoaInput;
import br.com.attornatus.api.mapper.PessoaMapper;
import br.com.attornatus.api.model.PessoaModel;
import br.com.attornatus.domain.entity.Pessoa;
import br.com.attornatus.domain.exception.ArgumentosInvalidosException;
import br.com.attornatus.domain.exception.PessoaNaoEncontradaException;
import br.com.attornatus.domain.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class PessoaService {

  private static final PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;

  private final PessoaRepository pessoaRepository;

  public List<PessoaModel> listar() {
    return pessoaMapper.toModel(pessoaRepository.findAll());
  }

  public PessoaModel consultar(Long id) {
    Pessoa pessoa = buscarOuFalhar(id);
    return pessoaMapper.toModel(pessoa);
  }

  public PessoaModel criar(PessoaInput pessoaInput) {
    Pessoa pessoa = pessoaMapper.toEntity(pessoaInput);
    if (isNull(pessoaInput) || isNull(pessoaInput.getNome()) || pessoaInput.getNome().isBlank()) {
      throw new ArgumentosInvalidosException();
    }
    return pessoaMapper.toModel(pessoaRepository.save(pessoa));
  }

  public PessoaModel editar(Long pessoaId, PessoaInput input) {
    Pessoa pessoaAAatualizar = buscarOuFalhar(pessoaId);
    Pessoa pessoaASalvar = pessoaMapper.toEntity(pessoaAAatualizar, input);
    return pessoaMapper.toModel(pessoaRepository.save(pessoaASalvar));
  }

  public Pessoa buscarOuFalhar(Long pessoaId) {
    return pessoaRepository
        .findById(pessoaId)
        .orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
  }
}
