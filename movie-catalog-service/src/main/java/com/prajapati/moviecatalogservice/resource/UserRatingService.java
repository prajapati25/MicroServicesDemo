package com.prajapati.moviecatalogservice.resource;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.prajapati.moviecatalogservice.model.Rating;
import com.prajapati.moviecatalogservice.model.UserRating;

@Service
public class UserRatingService {
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getUserRatingFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") }, threadPoolKey = "userRatingPool", threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "20"),
					@HystrixProperty(name = "maxQueueSize", value = "10") })
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingsdata/users/" + userId, UserRating.class);
	}

	public UserRating getUserRatingFallback(String userId) {
		return new UserRating(Arrays.asList(new Rating("No Movie", 0)));
	}

}
