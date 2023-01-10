package br.com.attornatus.controller;

import br.com.attornatus.PessoaModel;
import br.com.attornatus.entity.Pessoa;
import br.com.attornatus.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/pessoas")
@RequiredArgsConstructor
public class PessoaController {

  private final PessoaService pessoaService;

  @GetMapping
  public List<Pessoa> listar() {
    return pessoaService.listar();
  }


  @GetMapping("/{id}")
  public Pessoa consultar(@PathVariable Long id) {
    return pessoaService.consultar(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Pessoa criar(@RequestBody @Valid Pessoa pessoa) {
    return pessoaService.criar(pessoa);
  }

  @PutMapping("/{id}")
  public Pessoa editar(@PathVariable Long id, @RequestBody @Valid PessoaModel model) {
    return pessoaService.editar(id, model);
  }
}
