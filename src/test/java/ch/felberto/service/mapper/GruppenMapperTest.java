package ch.felberto.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GruppenMapperTest {

    private GruppenMapper gruppenMapper;

    @BeforeEach
    public void setUp() {
        gruppenMapper = new GruppenMapperImpl();
    }
}
