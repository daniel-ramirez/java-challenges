package com.applaudostudios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.applaudostudios.entities.Movie;

/**
 * MovieRepository is a rest repository and this is exposed
 * on URL http://serve-host:port/api/movies
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@RepositoryRestResource(path = "movies", collectionResourceRel = "movies")
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
