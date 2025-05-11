package com.flickfinder.controller;

import java.sql.SQLException;

import com.flickfinder.dao.MovieDAO;
import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.model.MovieRating;

import java.util.List;


import io.javalin.http.Context;

/**
 * The controller for the movie endpoints.
 * 
 * The controller acts as an intermediary between the HTTP routes and the DAO.
 * 
 * As you can see each method in the controller class is responsible for
 * handling a specific HTTP request.
 * 
 * Methods a Javalin Context object as a parameter and uses it to send a
 * response back to the client.
 * We also handle business logic in the controller, such as validating input and
 * handling errors.
 *
 * Notice that the methods don't return anything. Instead, they use the Javalin
 * Context object to send a response back to the client.
 */

public class MovieController {

	/**
	 * The movie data access object.
	 */

	private final MovieDAO movieDAO;

	/**
	 * Constructs a MovieController object and initializes the movieDAO.
	 */
	public MovieController(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	/**
	 * Returns a list of all movies in the database.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getAllMovies(Context ctx) {
		int limit = 50; // Default limit
		String limitParam = ctx.queryParam("limit");

		if (limitParam != null) {
			try {
				limit = Integer.parseInt(limitParam);
				if (limit <= 0) {
					ctx.status(400);
					ctx.result("Limit must be a positive integer");
					return;
				}
			} catch (NumberFormatException e) {
				ctx.status(400);
				ctx.result("Invalid limit value");
				return;
			}
		}

		try {
			List<Movie> movies = movieDAO.getAllMovies(limit);
			ctx.json(movies);
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}


	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getMovieById(Context ctx) {

		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			Movie movie = movieDAO.getMovieById(id);
			if (movie == null) {
				ctx.status(404);
				ctx.result("Movie not found");
				return;
			}
			ctx.json(movieDAO.getMovieById(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	/**
	 * Returns a list of stars of the movie with the specified id.
	 *
	 * @param ctx the Javalin context
	 */
	public void getPeopleByMovieId(Context ctx) {
		int movieId = Integer.parseInt(ctx.pathParam("id"));  // Get movie ID from URL
		try {
			List<Person> stars = movieDAO.getStarsByMovieId(movieId);  // Fetch stars from DAO
			if (stars == null || stars.isEmpty()) {  // Check for an empty list or null
				ctx.status(404);
				ctx.result("No stars found for this movie");
				return;
			}
			ctx.json(stars);  // Return the list of stars in JSON format
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.result("Invalid movie ID format");
		}
	}

	public void getRatingsByYear(Context ctx) {
		int limit = 50;
		int minVotes = 1000;

		// Parse year from path
		int year;
		try {
			year = Integer.parseInt(ctx.pathParam("year"));
		} catch (NumberFormatException e) {
			ctx.status(400).result("Invalid year");
			return;
		}

		// Optional limit param
		String limitParam = ctx.queryParam("limit");
		if (limitParam != null) {
			try {
				limit = Integer.parseInt(limitParam);
				if (limit <= 0) {
					ctx.status(400).result("Limit must be a positive integer");
					return;
				}
			} catch (NumberFormatException e) {
				ctx.status(400).result("Invalid limit value");
				return;
			}
		}

		// Optional votes param
		String votesParam = ctx.queryParam("votes");
		if (votesParam != null) {
			try {
				minVotes = Integer.parseInt(votesParam);
				if (minVotes < 0) {
					ctx.status(400).result("Votes must be a non-negative integer");
					return;
				}
			} catch (NumberFormatException e) {
				ctx.status(400).result("Invalid votes value");
				return;
			}
		}

		// Fetch from DAO
		try {
			List<MovieRating> movies = movieDAO.getRatingsByYear(year, minVotes, limit);
			ctx.json(movies);
		} catch (SQLException e) {
			ctx.status(500).result("Database error");
		}
	}


}