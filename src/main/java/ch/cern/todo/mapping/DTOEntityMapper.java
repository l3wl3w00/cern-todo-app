package ch.cern.todo.mapping;

import java.util.Collection;

public interface DTOEntityMapper<DTO,ENTITY> {
    DTO toDTO(ENTITY taskEntity);
    Collection<DTO> toDTOCollection(Collection<ENTITY> taskEntities);

    ENTITY toEntity(DTO taskDTO);
    Collection<ENTITY> toEntityCollection(Collection<DTO> taskDTOS);
}
