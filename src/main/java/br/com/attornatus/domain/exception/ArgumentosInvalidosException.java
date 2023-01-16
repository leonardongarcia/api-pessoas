package br.com.attornatus.domain.exception;

public class ArgumentosInvalidosException extends RuntimeException {
  public ArgumentosInvalidosException() {
    super("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");
  }
}
