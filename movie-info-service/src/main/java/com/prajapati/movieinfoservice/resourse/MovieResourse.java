package com.prajapati.movieinfoservice.resourse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajapati.movieinfoservice.model.Movie;

@RestController
@RequestMapping("/movies")
public class MovieResourse {
	@RequestMapping("/{movieId}")
	public Movie getMovie(@PathVariable("movieId") String movieId) {
		return new Movie(movieId, "Test name");
	}
}
