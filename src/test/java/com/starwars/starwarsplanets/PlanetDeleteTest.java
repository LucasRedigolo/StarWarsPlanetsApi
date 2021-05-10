package com.starwars.starwarsplanets;

import com.starwars.starwarsplanets.document.Film;
import com.starwars.starwarsplanets.document.Planet;
import com.starwars.starwarsplanets.repositories.PlanetRepository;
import com.starwars.starwarsplanets.services.impl.PlanetServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanetDeleteTest extends BaseTest{

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private PlanetServiceImpl planetService;

    @Test
    public void shouldDeleteAPlanet() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        String baseURL = "http://localhost:" + randomServerPort + "/api/planets/";
        URI uri = new URI(baseURL);

        final Planet planet = new Planet();
        planet.setName("Deletado");
        planet.setClimate("Frio");
        planet.setTerrain("Montanhoso");

        HttpEntity<Planet> request = new HttpEntity<>(planet);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        JSONObject jsonObj = new JSONObject(result.getBody());
        JSONObject planetAdded = jsonObj.getJSONObject("data");
        String planetId = planetAdded.getString("id");

        baseURL += planetId;
        URI deleteUri = new URI(baseURL);

        restTemplate.delete(deleteUri);

        Optional<Planet> planetDeletado = planetRepository.findById(planetId);

        assert planetDeletado.isEmpty();

    }
}
