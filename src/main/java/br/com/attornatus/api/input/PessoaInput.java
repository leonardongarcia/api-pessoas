package br.com.attornatus.api.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PessoaInput {

  @Schema(example = "Maria das Dores")
  @NotBlank
  private String nome;

  @Schema(type = "string", defaultValue = "25/03/1989")
  @JsonFormat(pattern = "dd/MM/yyyy")
  @NotBlank
  private String dataNascimento;
}
