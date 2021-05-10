package com.starwars.starwarsplanets.repositories;

import com.starwars.starwarsplanets.document.Film;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FilmRepository extends MongoRepository<Film, String> {
}
