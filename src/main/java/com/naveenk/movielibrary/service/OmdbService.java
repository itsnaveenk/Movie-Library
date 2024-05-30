package com.naveenk.movielibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OmdbService {

    private final String API_KEY = "b9bdc367";

    public String searchMovies(String title) {
        String url = String.format("http://www.omdbapi.com/?apikey=%s&s=%s", API_KEY, title);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    public String getMovieDetails(String imdbId) {
        String url = String.format("http://www.omdbapi.com/?apikey=%s&i=%s", API_KEY, imdbId);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
