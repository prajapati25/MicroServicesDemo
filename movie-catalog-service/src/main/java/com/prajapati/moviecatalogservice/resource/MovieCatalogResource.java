package com.prajapati.moviecatalogservice.resource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prajapati.moviecatalogservice.model.CatalogItem;
import com.prajapati.moviecatalogservice.model.Movie;
import com.prajapati.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private UserRatingService userRatingService;
	
	@Autowired
	private MovieService movieService;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		UserRating ratings = userRatingService.getUserRating(userId);
		System.out.println(ratings.getRatings().toArray());
		return ratings.getRatings().stream().map(rating -> {
			Movie movie = movieService.getMovies(rating);
			System.out.println(movie.toString());
			return new CatalogItem(movie.getName(), "Test", rating.getRating());
		}).collect(Collectors.toList());
	}
	/*
	 * Movie movie = webClientBuilder.build() .get()
	 * .uri("http://localhost:8082/movies/" + rating.getMovieId()) .retrieve()
	 * .bodyToMono(Movie.class) .block();
	 */
}
