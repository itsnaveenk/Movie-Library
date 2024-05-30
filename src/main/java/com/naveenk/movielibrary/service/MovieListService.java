package com.naveenk.movielibrary.service;

import com.naveenk.movielibrary.model.Movie;
import com.naveenk.movielibrary.model.MovieList;
import com.naveenk.movielibrary.model.User;
import com.naveenk.movielibrary.repository.MovieListRepository;
import com.naveenk.movielibrary.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieListService {

    @Autowired
    private MovieListRepository movieListRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<MovieList> getMovieListsByUser(User user) {
        return movieListRepository.findByUser(user);
    }

    public void saveMovieList(MovieList movieList) {
        movieListRepository.save(movieList);
    }

    public void addMovieToList(MovieList movieList, Movie movie) {
        movieList.getMovies().add(movie);
        movieListRepository.save(movieList);
    }

    public Movie findMovieByImdbId(String imdbId) {
        return movieRepository.findByImdbId(imdbId);
    }

    public MovieList findById(Long listId) {
        return movieListRepository.findById(listId).orElseThrow(() -> new NoSuchElementException("Movie list not found"));
    }
}
