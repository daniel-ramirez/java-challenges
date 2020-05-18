package com.applaudostudios.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.applaudostudios.entities.Movie;
import com.applaudostudios.repositories.MovieRepository;
import com.applaudostudios.repositories.querybuilder.GenericSpecification;
import com.applaudostudios.repositories.querybuilder.GenericSpecificationsBuilder;
import com.applaudostudios.util.CriteriaParser;

/**
 * MovieService is used for custom search in movies
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@Service
public class MovieService {

	private final Log logger = LogFactory.getLog(MovieAudLogService.class);

	@Autowired
	private MovieRepository movieRepository;

	/**
	 * Get list of movies filtering by conditions into search parameter and the list
	 * is paging
	 * 
	 * @param search
	 * @param pageable
	 */
	public Page<Movie> getCustomSearch(String search, Pageable pageable) {
		try {
			Specification<Movie> spec = null;
			if (search != null) {
				GenericSpecificationsBuilder<Movie> builder = new GenericSpecificationsBuilder<>();
				spec = builder.build(CriteriaParser.parse(search), GenericSpecification<Movie>::new);
			}
			return movieRepository.findAll(spec, pageable);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}
