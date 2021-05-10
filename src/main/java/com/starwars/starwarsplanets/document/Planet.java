package com.starwars.starwarsplanets.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planet {

     @Id
     private String id;

     @NonNull
     @Indexed(unique = true)
     private String name;

     @NonNull
     private String climate;

     @NonNull
     private String terrain;

     private List<Film> films; //An array of movies that this planet has appeared in.
}
