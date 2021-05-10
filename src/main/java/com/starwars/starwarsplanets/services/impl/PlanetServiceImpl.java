package com.starwars.starwarsplanets.services.impl;

import com.starwars.starwarsplanets.document.Film;
import com.starwars.starwarsplanets.document.Planet;
import com.starwars.starwarsplanets.repositories.PlanetRepository;
import com.starwars.starwarsplanets.services.PlanetService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanetServiceImpl implements PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private FilmServiceImpl filmService;

    String swApiSearchUrl = "https://swapi.dev/api/planets/?search=";


    @Override
    public List<Planet> listAll() {
        return this.planetRepository.findAll();
    }

    @Override
    public Optional<Planet> listById(String id) {
        return this.planetRepository.findById(id);
    }

    @Override
    public Optional<Planet> listByName(String name) {
        return this.planetRepository.findPlanetByName(name);
    }

    @Override
    public Planet addPlanet(Planet planet) throws Exception {
        getPlanetDetails(planet);
        return this.planetRepository.save(planet);
    }

    @Override
    public Planet updatePlanet(Planet planet) {
        Optional<Planet> planets = this.planetRepository.findById(planet.getId());

        if(planets.isPresent()){
            planet.setName(planet.getName() == null ? planets.get().getName() : planet.getName());
            planet.setTerrain(planet.getTerrain() == null ? planets.get().getTerrain() : planet.getTerrain());
            planet.setClimate(planet.getClimate() == null ? planets.get().getClimate() : planet.getClimate());
            planet.setFilms(planet.getFilms() == null ? planets.get().getFilms() : planet.getFilms());

            return this.planetRepository.save(planet);
        }
        return this.planetRepository.save(planet);
    }

    @Override
    public void deletePlanet(String id) {
        this.planetRepository.deleteById(id);
    }


    public void getPlanetDetails(Planet planet) throws Exception {

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(swApiSearchUrl + planet.getName(), String.class);

        try {
            JSONObject jsonObj = new JSONObject(responseEntity.getBody());
            JSONArray ja_data = jsonObj.getJSONArray("results");
            int length = ja_data.length();

            if(length > 0){
                for (int i = 0; i < length; i++) {

                    JSONObject planetObject = ja_data.getJSONObject(i);

                    planet.setClimate(planetObject.getString("climate"));
                    planet.setName(planetObject.getString("name"));
                    planet.setTerrain(planetObject.getString("terrain"));

                    JSONArray filmsArray = planetObject.getJSONArray("films");
                    List<Film> filmList = new ArrayList<>();

                    if (filmsArray != null) {

                        for (int f = 0; f < filmsArray.length(); f++) {
                            Film film = new Film();
                            String filmURL = filmsArray.get(f).toString();
                            Film filmParsed = filmURLParser(filmURL);
                            film.setTitle(filmParsed.getTitle());
                            film.setEpisodeId(filmParsed.getEpisodeId());
                            filmList.add(film);
                        }
                    }
                    planet.setFilms(filmList);

                }

            }

        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Film filmURLParser(String filmURL) throws Exception {

        filmURL = filmURL.replace("http", "https");
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(filmURL, String.class);
        return this.filmService.filmURLResolver(responseEntity);

    }
}
