package br.com.attornatus.api.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EnderecoPrincipalInput {

  @Schema(example = "R. Miguel Inácio Faraco")
  private String logradouro;

  @Schema(example = "88705-050")
  private String cep;

  @Schema(example = "355")
  private Integer numero;

  @Schema(example = "Tubarão ")
  private String cidade;

  @Schema(example = "true")
  private Boolean principal;
}
