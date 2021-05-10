package com.starwars.starwarsplanets.repositories;

import com.starwars.starwarsplanets.document.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlanetRepository extends MongoRepository<Planet, String>{

    Optional<Planet> findPlanetByName(String name);
}
