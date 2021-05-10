package com.starwars.starwarsplanets.controllers;


import com.starwars.starwarsplanets.document.Film;
import com.starwars.starwarsplanets.responses.Response;
import com.starwars.starwarsplanets.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/films")
public class FilmController {

    @Autowired
    private FilmService filmServices;

    @GetMapping()
    public ResponseEntity<Response<List<Film>>> listAll() throws Exception {

        return ResponseEntity.ok(new Response<>(this.filmServices.listAll()));
    }
}
