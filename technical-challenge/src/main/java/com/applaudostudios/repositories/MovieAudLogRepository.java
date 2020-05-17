package com.applaudostudios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.applaudostudios.entities.MovieAudLog;
import com.applaudostudios.entities.MovieAudLogId;

/**
 * MovieAudLogRepository is repository and this is not exposed in the API
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@Repository
public interface MovieAudLogRepository extends JpaRepository<MovieAudLog, MovieAudLogId> {

}
