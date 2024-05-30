package com.naveenk.movielibrary.repository;

import com.naveenk.movielibrary.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByImdbId(String imdbId);
}