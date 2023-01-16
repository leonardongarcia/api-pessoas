package br.com.attornatus.api.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EnderecoInput {

  @Schema(example = "R. Miguel Inácio Faraco")
  @NotBlank
  private String logradouro;

  @Schema(example = "88705-050")
  @NotBlank
  private String cep;

  @Schema(example = "355")
  @NotNull
  private Integer numero;

  @Schema(example = "Tubarão ")
  @NotBlank
  private String cidade;

  @Schema(example = "true")
  @NotNull
  private Boolean principal;
}
