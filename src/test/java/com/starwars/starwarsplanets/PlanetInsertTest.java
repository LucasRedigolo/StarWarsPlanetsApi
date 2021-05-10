package com.starwars.starwarsplanets;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.starwars.starwarsplanets.document.Film;
import com.starwars.starwarsplanets.document.Planet;
import com.starwars.starwarsplanets.repositories.PlanetRepository;
import com.starwars.starwarsplanets.services.PlanetService;
import com.starwars.starwarsplanets.services.impl.PlanetServiceImpl;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanetInsertTest extends BaseTest {


    @LocalServerPort
    int randomServerPort;

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private PlanetServiceImpl planetService;

    @Test
    public void shouldCreateAPlanetWithoutFilms() throws URISyntaxException, JSONException {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/planets/";
        URI uri = new URI(baseUrl);

        final Planet planet = new Planet();
        planet.setName("Planeta Teste");
        planet.setClimate("Frio");
        planet.setTerrain("Montanhoso");

        HttpEntity<Planet> request = new HttpEntity<>(planet);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        JSONObject jsonObj = new JSONObject(result.getBody());
        JSONObject planetRequested = jsonObj.getJSONObject("data");

        Planet planetInserted = planetRepository.save(planet);

        Assert.assertEquals(planetInserted.getName(), planetRequested.getString("name"));
        Assert.assertEquals(planetInserted.getClimate(), planetRequested.getString("climate"));
        Assert.assertEquals(planetInserted.getTerrain(), planetRequested.getString("terrain"));
        Assert.assertNull(planetInserted.getFilms());

    }

    @Test
    public void shouldInsertARealPlanetWithItsDetails() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/planets/";
        URI uri = new URI(baseUrl);

        final Planet planet = new Planet();
        planet.setName("Tatooine");
        planet.setClimate("Frio");
        planet.setTerrain("Montanhoso");

        HttpEntity<Planet> request = new HttpEntity<>(planet);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        JSONObject jsonObj = new JSONObject(result.getBody());
        JSONObject planetRequested = jsonObj.getJSONObject("data");

        Planet planetInserted = this.planetService.addPlanet(planet);

        Assert.assertEquals(planetInserted.getName(), planetRequested.getString("name"));
        Assert.assertEquals(planetInserted.getClimate(), planetRequested.getString("climate"));
        Assert.assertEquals(planetInserted.getTerrain(), planetRequested.getString("terrain"));

    }
}