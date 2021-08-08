package ch.felberto.service.mapper;

import ch.felberto.domain.*;
import ch.felberto.service.dto.RangierungDTO;
import java.util.List;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rangierung} and its DTO {@link RangierungDTO}.
 */
@Mapper(componentModel = "spring", uses = { WettkampfMapper.class })
public interface RangierungMapper extends EntityMapper<RangierungDTO, Rangierung> {
    @Mapping(target = "wettkampf", source = "wettkampf", qualifiedByName = "name")
    RangierungDTO toDto(Rangierung s);
}
