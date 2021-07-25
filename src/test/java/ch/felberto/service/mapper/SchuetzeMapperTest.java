package ch.felberto.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchuetzeMapperTest {

    private SchuetzeMapper schuetzeMapper;

    @BeforeEach
    public void setUp() {
        schuetzeMapper = new SchuetzeMapperImpl();
    }
}
