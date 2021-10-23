package com.prajapati.moviecatalogservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.prajapati.moviecatalogservice.model.Movie;
import com.prajapati.moviecatalogservice.model.Rating;

@Service
public class MovieService {
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getMoviesFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") }, threadPoolKey = "movieInfoPool", threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "20"),
					@HystrixProperty(name = "maxQueueSize", value = "10") })
	public Movie getMovies(Rating rating) {
		return restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" + rating.getMovieId(), Movie.class);
	}

	public Movie getMoviesFallback(Rating rating) {
		return new Movie(rating.getMovieId(), "No movie");
	}
}
