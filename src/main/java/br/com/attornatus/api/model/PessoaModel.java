package br.com.attornatus.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaModel {

  @Schema(example = "1")
  private Long id;

  @Schema(example = "Maria das dores")
  private String nome;

  @Schema(type = "string", defaultValue = "25/03/1989")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;
}
