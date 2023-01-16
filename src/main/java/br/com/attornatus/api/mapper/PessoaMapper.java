package br.com.attornatus.api.mapper;

import br.com.attornatus.api.input.PessoaInput;
import br.com.attornatus.api.model.PessoaEnderecoModel;
import br.com.attornatus.api.model.PessoaEnderecoPrincipalModel;
import br.com.attornatus.api.model.PessoaModel;
import br.com.attornatus.domain.entity.Endereco;
import br.com.attornatus.domain.entity.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE)
public interface PessoaMapper {

  PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

  @Mapping(target = "enderecos", ignore = true)
  @Mapping(target = "dataNascimento", dateFormat = "dd/MM/yyyy")
  Pessoa toEntity(@MappingTarget Pessoa pessoa, PessoaInput model);

  @Mapping(target = "enderecos", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "dataNascimento", dateFormat = "dd/MM/yyyy")
  Pessoa toEntity(PessoaInput pessoaInput);

  PessoaModel toModel(Pessoa pessoa);

  @Mapping(source = "pessoa.id", target = "id")
  PessoaEnderecoModel toModel(Pessoa pessoa, Endereco endereco);

  List<PessoaModel> toModel(List<Pessoa> pessoas);

  PessoaEnderecoModel toEnderecoModel(Pessoa pessoa);

  @Mapping(source = "pessoa.id", target = "id")
  PessoaEnderecoPrincipalModel toPrincipal(Pessoa pessoa, Endereco endereco);

  Pessoa principaltoEntity(PessoaEnderecoPrincipalModel pessoaEnderecoPrincipalModel);
}
