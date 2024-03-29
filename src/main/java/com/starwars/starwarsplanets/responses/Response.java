package com.starwars.starwarsplanets.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response<T> {

    private T data;
    private List<String> errors;

    public Response (T data){
        this.data = data;
    }

    public Response(List<String> errors) {
        this.errors = errors;
    }
}
