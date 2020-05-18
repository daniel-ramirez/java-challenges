package com.applaudostudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.applaudostudios.entities.Movie;
import com.applaudostudios.services.MovieService;

/**
 * MovieController expose an endpoint to build custom search; this is exposed on
 * URL http://serve-host:port/api/movies/search/custom-filter
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@RestController
@RequestMapping(value = "/api")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@GetMapping("/movies/search/custom-filter")
	@ResponseBody
	public Page<Movie> getCustomSearch(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		return movieService.getCustomSearch(search, pageable);
	}

}
