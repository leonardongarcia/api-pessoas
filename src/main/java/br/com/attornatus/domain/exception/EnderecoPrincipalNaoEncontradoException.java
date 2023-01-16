package br.com.attornatus.domain.exception;

public class EnderecoPrincipalNaoEncontradoException extends RuntimeException {
  public EnderecoPrincipalNaoEncontradoException(Long pessoaId) {
    super(String.format("Não há endereço principal cadastrado para a pessoa com id %d", pessoaId));
  }
}
