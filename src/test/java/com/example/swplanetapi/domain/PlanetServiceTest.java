package com.example.swplanetapi.domain;

import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

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

        Planet sut = planetService.create(PlanetConstants.PLANET);

        assertThat(sut).isEqualTo(PlanetConstants.PLANET);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        when(planetRepository.findById(PlanetConstants.ID_PLANET)).thenReturn(Optional.of(PlanetConstants.PLANET));

        Optional<Planet> sut = planetService.findById(PlanetConstants.ID_PLANET);
    
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PlanetConstants.PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() {
        when(planetRepository.findById(PlanetConstants.ID_PLANET)).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.findById(PlanetConstants.ID_PLANET);
    
        assertThat(sut).isEmpty();
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRepository.save(PlanetConstants.INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(PlanetConstants.INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }
}
