package com.starwars.starwarsplanets.services.impl;

import com.starwars.starwarsplanets.document.Film;
import com.starwars.starwarsplanets.services.FilmService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    RestTemplate restTemplate;

    String BASE_URL = "https://swapi.dev/api/films/";
    List<Film> filmList = new ArrayList<>() ;

    @Override
    public List<Film> listAll() throws Exception {

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(BASE_URL, String.class);

        return getFilmList(responseEntity);
    }

    public List<Film> getFilmList(ResponseEntity<String> responseEntity) throws Exception {
        try {
            JSONObject jsonObj = new JSONObject(responseEntity.getBody());

            JSONArray ja_data = jsonObj.getJSONArray("results");
            int length = ja_data.length();

            for (int i = 0; i < length; i++) {
                Film film = new Film();
                JSONObject filmObject = ja_data.getJSONObject(i);
                film.setTitle(filmObject.getString("title"));
                film.setEpisodeId(filmObject.getInt("episode_id"));

                this.filmList.add(film);
            }
            return this.filmList;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Film filmURLResolver(ResponseEntity<String> responseEntity) throws Exception {
        try {
            JSONObject jsonObj = new JSONObject(responseEntity.getBody());
            Film film = new Film();

            film.setTitle(jsonObj.getString("title"));
            film.setEpisodeId(jsonObj.getInt("episode_id"));

            return film;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
