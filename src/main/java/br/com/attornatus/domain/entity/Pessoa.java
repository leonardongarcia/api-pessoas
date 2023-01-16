package br.com.attornatus.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;

  @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
  private List<Endereco> enderecos = new ArrayList<>();

  public void adicionarEndereco(Endereco endereco) {
    this.enderecos.add(endereco);
  }
}
