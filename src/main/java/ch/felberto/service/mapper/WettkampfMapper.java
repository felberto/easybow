package ch.felberto.service.mapper;

import ch.felberto.domain.*;
import ch.felberto.service.dto.WettkampfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wettkampf} and its DTO {@link WettkampfDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WettkampfMapper extends EntityMapper<WettkampfDTO, Wettkampf> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    WettkampfDTO toDtoName(Wettkampf wettkampf);
}
