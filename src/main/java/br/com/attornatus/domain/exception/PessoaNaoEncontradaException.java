package br.com.attornatus.domain.exception;

public class PessoaNaoEncontradaException extends RuntimeException {
  public PessoaNaoEncontradaException(Long id) {
    super(String.format("Pessoa com id %d não foi encontrada!", id));
  }
}
