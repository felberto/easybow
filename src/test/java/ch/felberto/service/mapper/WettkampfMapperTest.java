package ch.felberto.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WettkampfMapperTest {

    private WettkampfMapper wettkampfMapper;

    @BeforeEach
    public void setUp() {
        wettkampfMapper = new WettkampfMapperImpl();
    }
}
