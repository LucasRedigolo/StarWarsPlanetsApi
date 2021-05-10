package com.starwars.starwarsplanets.services;

import com.starwars.starwarsplanets.document.Planet;

import java.util.List;
import java.util.Optional;

public interface PlanetService {

    List<Planet> listAll();
    Optional<Planet> listById(String id);
    Optional<Planet> listByName(String name);
    Planet addPlanet(Planet planet) throws Exception;
    Planet updatePlanet (Planet planet);

    void deletePlanet (String id);
}
