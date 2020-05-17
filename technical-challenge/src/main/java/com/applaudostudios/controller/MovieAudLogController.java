package com.applaudostudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.applaudostudios.entities.MovieAudLog;
import com.applaudostudios.services.MovieAudLogService;

/**
 * MovieAudLogController is only to get movie update logs and this is exposed on
 * URL http://serve-host:port/api/movie-aud-logs
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@RestController
@RequestMapping(value = "/api")
public class MovieAudLogController {

	@Autowired
	private MovieAudLogService movieAudLogService;

	@GetMapping("/movie-aud-logs")
	@ResponseBody
	public Page<MovieAudLog> getMovieAudLogs(Pageable pageable) {
		return movieAudLogService.getMovieAudLogs(pageable);
	}

}
