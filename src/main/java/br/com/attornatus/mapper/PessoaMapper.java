package br.com.attornatus.mapper;

import br.com.attornatus.PessoaModel;
import br.com.attornatus.entity.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE, componentModel = "spring")
public interface PessoaMapper {

  @Mapping(target = "endereco", ignore = true)
  Pessoa toEntity(@MappingTarget Pessoa pessoa, PessoaModel model);
}
