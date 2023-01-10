package br.com.attornatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaModel {

  private Long id;

  private String nome;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;
}
