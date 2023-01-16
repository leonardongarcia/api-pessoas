package br.com.attornatus.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
  MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível!"),
  ENDERECO_NAO_ENCONTRADO(
      "/endereco-principal-nao-encontrado", "Endereço principal não encontrado!"),
  PESSOA_NAO_ENCONTRADA("/pessoa-nao-encontrada", "Pessoa não encontrada!"),
  PRINCIPAL_JA_CADASTRADO("/endereco-principal-ja-cadastrado", "Endereço principal já cadastrado"),
  PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido!"),
  ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema!"),
  DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos!");

  private final String title;
  private final String uri;

  ProblemType(String path, String title) {
    this.uri = "https://www.attornatus.com.br" + path;
    this.title = title;
  }
}
