package ch.felberto.service.mapper;

import ch.felberto.domain.*;
import ch.felberto.service.dto.ResultateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Resultate} and its DTO {@link ResultateDTO}.
 */
@Mapper(componentModel = "spring", uses = { PassenMapper.class, GruppenMapper.class, SchuetzeMapper.class, WettkampfMapper.class })
public interface ResultateMapper extends EntityMapper<ResultateDTO, Resultate> {
    @Mapping(target = "passe1", source = "passe1", qualifiedByName = "id")
    @Mapping(target = "passe2", source = "passe2", qualifiedByName = "id")
    @Mapping(target = "passe3", source = "passe3", qualifiedByName = "id")
    @Mapping(target = "passe4", source = "passe4", qualifiedByName = "id")
    @Mapping(target = "gruppe", source = "gruppe", qualifiedByName = "name")
    @Mapping(target = "schuetze", source = "schuetze", qualifiedByName = "name")
    @Mapping(target = "wettkampf", source = "wettkampf", qualifiedByName = "name")
    ResultateDTO toDto(Resultate s);
}
