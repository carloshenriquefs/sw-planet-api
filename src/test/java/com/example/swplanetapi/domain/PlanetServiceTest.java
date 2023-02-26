package com.example.swplanetapi.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

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
    public void getPlanet_ByInexistingId_ReturnsEmpty() {
        when(planetRepository.findById(PlanetConstants.ID_PLANET)).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.findById(PlanetConstants.ID_PLANET);
    
        assertThat(sut).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        when(planetRepository.findByName(PlanetConstants.NAME_PLANET)).thenReturn(Optional.of(PlanetConstants.PLANET));

        Optional<Planet> sut = planetService.findByName(PlanetConstants.NAME_PLANET);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PlanetConstants.PLANET);
    }

    @Test
    public void getPlanet_ByInexistingName_ReturnsEmpty() {
        when(planetRepository.findByName(PlanetConstants.NAME_PLANET)).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.findByName(PlanetConstants.NAME_PLANET);

        assertThat(sut).isEmpty();
    }

    @Test
    public void listPlanets_RetursAllPlanets() {
        List<Planet> planets = new ArrayList<>() {
            {
                add(PlanetConstants.PLANET);
            }
        };

        Example<Planet> query = QueryBuilder
                .makeQuery(new Planet(PlanetConstants.PLANET.getClimate(), PlanetConstants.PLANET.getTerrain()));

        when(planetRepository.findAll(query)).thenReturn(planets);

        List<Planet> sut = planetService.findAll(PlanetConstants.PLANET.getTerrain(),
                PlanetConstants.PLANET.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PlanetConstants.PLANET);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() {
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.findAll(PlanetConstants.PLANET.getTerrain(), PlanetConstants.PLANET.getClimate());

        assertThat(sut).isEmpty();
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRepository.save(PlanetConstants.INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(PlanetConstants.INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void removePlanet_WithExistingId_doesNotThrowAnyException() {
        assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);

        assertThatThrownBy(() -> planetService.remove(99L)).isInstanceOf(RuntimeException.class);
    }
}
