package com.example.swplanetapi.domain;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.swplanetapi.common.PlanetConstants;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
 
    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        when(planetRepository.save(PlanetConstants.PLANET)).thenReturn(PlanetConstants.PLANET);

        Planet planet = planetService.create(PlanetConstants.PLANET);

        Assertions.assertThat(planet).isEqualTo(PlanetConstants.PLANET);
    }
}
