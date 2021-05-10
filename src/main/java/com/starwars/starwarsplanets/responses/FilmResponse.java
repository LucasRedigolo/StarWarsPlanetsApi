package com.starwars.starwarsplanets.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.starwars.starwarsplanets.document.Film;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmResponse {

    private String json;
    @JsonProperty("results")
    private Film results;

}
