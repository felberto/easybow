package ch.felberto.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RangierungMapperTest {

    private RangierungMapper rangierungMapper;

    @BeforeEach
    public void setUp() {
        rangierungMapper = new RangierungMapperImpl();
    }
}
