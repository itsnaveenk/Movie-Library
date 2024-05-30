package com.naveenk.movielibrary.controller;

import com.naveenk.movielibrary.model.Movie;
import com.naveenk.movielibrary.model.MovieList;
import com.naveenk.movielibrary.model.User;
import com.naveenk.movielibrary.service.MovieListService;
import com.naveenk.movielibrary.service.OmdbService;
import com.naveenk.movielibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private OmdbService omdbService;

    @Autowired
    private MovieListService movieListService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<?> searchMovies(@RequestParam String title) {
        String response = omdbService.searchMovies(title);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/lists")
    public ResponseEntity<?> createMovieList(@RequestBody MovieList movieList, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        movieList.setUser(user);
        movieListService.saveMovieList(movieList);
        return ResponseEntity.ok("Movie list created");
    }

    @PostMapping("/lists/{listId}/movies")
    public ResponseEntity<?> addMovieToList(@PathVariable Long listId, @RequestBody Movie movie) {
        MovieList movieList = movieListService.findById(listId);
        Movie existingMovie = movieListService.findMovieByImdbId(movie.getImdbId());

        if (existingMovie == null) {
            movieListService.addMovieToList(movieList, movie);
        } else {
            movieListService.addMovieToList(movieList, existingMovie);
        }

        return ResponseEntity.ok("Movie added to list");
    }

    @GetMapping("/lists")
    public ResponseEntity<?> getMovieLists(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<MovieList> movieLists = movieListService.getMovieListsByUser(user);
        return ResponseEntity.ok(movieLists);
    }
}
