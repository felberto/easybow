package ch.felberto.service.mapper;

import ch.felberto.domain.*;
import ch.felberto.service.dto.SchuetzeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Schuetze} and its DTO {@link SchuetzeDTO}.
 */
@Mapper(componentModel = "spring", uses = { VereinMapper.class })
public interface SchuetzeMapper extends EntityMapper<SchuetzeDTO, Schuetze> {
    @Mapping(target = "verein", source = "verein", qualifiedByName = "name")
    SchuetzeDTO toDto(Schuetze s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SchuetzeDTO toDtoName(Schuetze schuetze);
}
