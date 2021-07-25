package ch.felberto.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassenMapperTest {

    private PassenMapper passenMapper;

    @BeforeEach
    public void setUp() {
        passenMapper = new PassenMapperImpl();
    }
}
