package com.starwars.starwarsplanets;

import com.starwars.starwarsplanets.document.Planet;
import com.starwars.starwarsplanets.repositories.PlanetRepository;
import com.starwars.starwarsplanets.services.impl.PlanetServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanetUpdateTest extends BaseTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    public void shouldUpdateAPlanet() throws URISyntaxException, JSONException {

        RestTemplate restTemplate = new RestTemplate();

        String baseURL = "http://localhost:" + randomServerPort + "/api/planets/";
        URI uri = new URI(baseURL);

        final Planet planet = new Planet();
        planet.setName("Atualizado");
        planet.setClimate("Quente");
        planet.setTerrain("Plano");

        HttpEntity<Planet> request = new HttpEntity<>(planet);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        JSONObject jsonObj = new JSONObject(result.getBody());
        JSONObject planetAdded = jsonObj.getJSONObject("data");
        String planetId = planetAdded.getString("id");

        baseURL += planetId;
        URI updateUri = new URI(baseURL);

        planet.setClimate("Frio");
        HttpEntity<Planet> requestUpdate = new HttpEntity<>(planet);

        restTemplate.put(updateUri, requestUpdate);

        Optional<Planet> planetAtualizado = planetRepository.findById(planetId);

        assert planetAtualizado.get().getClimate().equals("Frio");
    }
}
