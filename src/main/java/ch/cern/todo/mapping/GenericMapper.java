package ch.cern.todo.mapping;

import org.mapstruct.Mapper;

import java.util.Collection;

public interface GenericMapper<DTO,ENTITY> {
    DTO toDTO(ENTITY entity);
    Collection<DTO> toDTOCollection(Collection<ENTITY> entities);

    ENTITY toEntity(DTO dto);
    Collection<ENTITY> toEntityCollection(Collection<DTO> dtos);


}
