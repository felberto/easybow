package ch.felberto.service.mapper;

import ch.felberto.domain.*;
import ch.felberto.service.dto.VereinDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Verein} and its DTO {@link VereinDTO}.
 */
@Mapper(componentModel = "spring", uses = { VerbandMapper.class })
public interface VereinMapper extends EntityMapper<VereinDTO, Verein> {
    @Mapping(target = "verband", source = "verband", qualifiedByName = "name")
    VereinDTO toDto(Verein s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    VereinDTO toDtoName(Verein verein);
}
