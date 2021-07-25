package ch.felberto.service.mapper;

import ch.felberto.domain.*;
import ch.felberto.service.dto.VerbandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Verband} and its DTO {@link VerbandDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VerbandMapper extends EntityMapper<VerbandDTO, Verband> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    VerbandDTO toDtoName(Verband verband);
}
