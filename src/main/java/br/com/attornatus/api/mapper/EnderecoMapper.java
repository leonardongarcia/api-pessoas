package br.com.attornatus.api.mapper;

import br.com.attornatus.api.input.EnderecoInput;
import br.com.attornatus.api.input.EnderecoPrincipalInput;
import br.com.attornatus.api.model.EnderecoModel;
import br.com.attornatus.domain.entity.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE)
public interface EnderecoMapper {

  EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

  @Mapping(target = "pessoa", ignore = true)
  @Mapping(target = "id", ignore = true)
  Endereco toEntity(EnderecoInput enderecoInput);

  Endereco toEntity(EnderecoModel endereco);

  Endereco principalToEntity(
      @MappingTarget Endereco endereco, EnderecoPrincipalInput enderecoInput);

  EnderecoModel toModel(Endereco endereco);
}
