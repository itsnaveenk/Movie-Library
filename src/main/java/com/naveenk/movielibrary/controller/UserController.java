package com.naveenk.movielibrary.controller;

import com.naveenk.movielibrary.model.MovieList;
import com.naveenk.movielibrary.model.User;
import com.naveenk.movielibrary.service.MovieListService;
import com.naveenk.movielibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showLandingPage() {
        System.out.println("Welcome to Movie Library");

        return "index";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "index";
    }

    @Autowired
private MovieListService movieListService;

@GetMapping("/home")
public String home(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("movieLists", movieListService.getMovieListsByUser(user));
        return "home";
    } else {
        return "redirect:/login";
    }
}

@PostMapping("/movieLists/create")
public String createMovieList(@RequestParam String name, @RequestParam(required = false) boolean isPublic) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {
        User user = userService.findByUsername(auth.getName());
        MovieList movieList = new MovieList();
        movieList.setName(name);
        movieList.setPublic(isPublic);
        movieList.setUser(user);
        movieListService.saveMovieList(movieList);
        return "redirect:/home";
    } else {
        return "redirect:/login";
    }
}

@GetMapping("/movieLists/{id}")
public String viewMovieList(@PathVariable Long id, Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {
        User user = userService.findByUsername(auth.getName());
        MovieList movieList = movieListService.findById(id);
        if (movieList.isPublic() || movieList.getUser().equals(user)) {
            model.addAttribute("movieList", movieList);
            return "movie_list_details";
        }
    }
    return "redirect:/login";
}

}