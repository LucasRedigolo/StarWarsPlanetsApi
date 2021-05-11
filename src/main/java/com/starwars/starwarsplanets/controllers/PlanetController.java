package com.starwars.starwarsplanets.controllers;

import com.starwars.starwarsplanets.document.Planet;
import com.starwars.starwarsplanets.responses.Response;
import com.starwars.starwarsplanets.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @GetMapping
    public ResponseEntity<Response<List<Planet>>> listAll(){
        return ResponseEntity.ok( new Response<>(this.planetService.listAll()));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Response<Planet>> listById(@PathVariable(name = "id") String id){
        Optional<Planet> planetOptional = this.planetService.listById(id);
        return ResponseEntity.ok( new Response<>(planetOptional.orElse(null)));
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<Response<Planet>> listByName(@PathVariable(name = "name") String name){
        Optional<Planet> planetOptional = this.planetService.listByName(name);
        return ResponseEntity.ok( new Response<>(planetOptional.orElse(null)));
    }

    @PostMapping
    public ResponseEntity<Response<Planet>> addPlanet(@Validated @RequestBody Planet planet,
                                              BindingResult result) throws Exception {
        if (result.hasErrors()){
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach( error -> errors.add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<>(errors));
        }
        return ResponseEntity.ok(new Response<>(this.planetService.addPlanet(planet)));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Response<Planet>> updatePlanet(@PathVariable(name="id") String id,
                                               @Validated @RequestBody Planet planet,
                                               BindingResult result){
        if (result.hasErrors()){
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach( error -> errors.add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<>(errors));
        }
        planet.setId(id);
        return ResponseEntity.ok(new Response<>(this.planetService.updatePlanet(planet)));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response<String>> deletePlanet(@PathVariable(name = "id") String id){
        this.planetService.deletePlanet(id);
        return ResponseEntity.ok( new Response<>("Planeta de id " + id + " excluido com sucesso."));
    }

}
