package ch.felberto.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VereinMapperTest {

    private VereinMapper vereinMapper;

    @BeforeEach
    public void setUp() {
        vereinMapper = new VereinMapperImpl();
    }
}
