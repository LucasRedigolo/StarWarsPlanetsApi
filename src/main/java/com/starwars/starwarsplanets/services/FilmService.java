package com.starwars.starwarsplanets.services;

import com.starwars.starwarsplanets.document.Film;

import java.util.List;

public interface FilmService
{

    List<Film>  listAll() throws Exception;

}
