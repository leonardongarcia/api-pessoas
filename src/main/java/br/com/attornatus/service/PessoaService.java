package br.com.attornatus.service;

import br.com.attornatus.PessoaModel;
import br.com.attornatus.entity.Pessoa;
import br.com.attornatus.exception.PessoaNaoEncontradaException;
import br.com.attornatus.mapper.PessoaMapper;
import br.com.attornatus.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

  private final PessoaRepository pessoaRepository;

  private final PessoaMapper pessoaMapper;

  public List<Pessoa> listar() {
    return pessoaRepository.findAll();
  }

  public Pessoa criar(Pessoa pessoa) {
    return pessoaRepository.save(pessoa);
  }

  public Pessoa consultar(Long id) {
    return buscarOuFalhar(id);
  }



  private Pessoa buscarOuFalhar(Long id) {
    return pessoaRepository.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException(id));
  }
}
