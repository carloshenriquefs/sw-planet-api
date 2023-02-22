package com.example.swplanetapi.domain;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.swplanetapi.common.PlanetConstants;

@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {
    
    @Autowired
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        Planet planet = planetService.create(PlanetConstants.PLANET);

        Assertions.assertThat(planet).isEqualTo(PlanetConstants.PLANET);
    }
}
