package ch.felberto.service.mapper;

import ch.felberto.domain.*;
import ch.felberto.service.dto.PassenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Passen} and its DTO {@link PassenDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PassenMapper extends EntityMapper<PassenDTO, Passen> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PassenDTO toDtoId(Passen passen);
}
