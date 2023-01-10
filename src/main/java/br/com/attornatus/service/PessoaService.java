package br.com.attornatus.service;

import br.com.attornatus.entity.Pessoa;
import br.com.attornatus.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaService {

  private final PessoaRepository pessoaRepository;

  public Pessoa criar(Pessoa pessoa) {
    return pessoaRepository.save(pessoa);
  }
}
