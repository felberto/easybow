package ch.felberto.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VerbandMapperTest {

    private VerbandMapper verbandMapper;

    @BeforeEach
    public void setUp() {
        verbandMapper = new VerbandMapperImpl();
    }
}
