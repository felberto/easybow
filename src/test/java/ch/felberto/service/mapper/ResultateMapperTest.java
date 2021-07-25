package ch.felberto.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResultateMapperTest {

    private ResultateMapper resultateMapper;

    @BeforeEach
    public void setUp() {
        resultateMapper = new ResultateMapperImpl();
    }
}
