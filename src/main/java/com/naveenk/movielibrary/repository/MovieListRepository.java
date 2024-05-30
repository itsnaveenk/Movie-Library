package com.naveenk.movielibrary.repository;

import com.naveenk.movielibrary.model.MovieList;
import com.naveenk.movielibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieListRepository extends JpaRepository<MovieList, Long> {
    List<MovieList> findByUser(User user);
}