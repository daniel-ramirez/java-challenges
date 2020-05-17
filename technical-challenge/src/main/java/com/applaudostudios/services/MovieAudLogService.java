package com.applaudostudios.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.applaudostudios.entities.MovieAudLog;
import com.applaudostudios.repositories.MovieAudLogRepository;

/**
 * MovieAudLogService is used to manage process to get change logs from Movie
 * entity
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@Service
public class MovieAudLogService {

	private final Log logger = LogFactory.getLog(MovieAudLogService.class);

	@Autowired
	private MovieAudLogRepository movieAudLogRepository;

	/**
	 * Get list of change logs from all Movies and the list is paging
	 * 
	 * @param pageable
	 */
	public Page<MovieAudLog> getMovieAudLogs(Pageable pageable) {
		try {
			return movieAudLogRepository.findAll(pageable);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}
