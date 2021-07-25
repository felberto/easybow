package ch.felberto.service.mapper;

import ch.felberto.domain.*;
import ch.felberto.service.dto.GruppenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gruppen} and its DTO {@link GruppenDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GruppenMapper extends EntityMapper<GruppenDTO, Gruppen> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GruppenDTO toDtoName(Gruppen gruppen);
}
