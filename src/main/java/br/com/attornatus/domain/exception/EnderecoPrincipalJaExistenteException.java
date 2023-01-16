package br.com.attornatus.domain.exception;

public class EnderecoPrincipalJaExistenteException extends RuntimeException {
  public EnderecoPrincipalJaExistenteException(Long enderecoId, Long pessoaId) {
    super(
        String.format(
            "Já existe um endereço principal cadastrado! "
                + "Por favor altere o campo 'principal' para 'false' do endereço %d da pessoa com id %d, "
                + "e tente novamente!",
            enderecoId, pessoaId));
  }
}
