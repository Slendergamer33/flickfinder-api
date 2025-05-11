package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.util.Database;
import com.flickfinder.model.Person;
import com.flickfinder.model.MovieRating;




/**
 * The Data Access Object for the Movie table.
 * 
 * This class is responsible for getting data from the Movies table in the
 * database.
 * 
 */
public class MovieDAO {

	/**
	 * The connection to the database.
	 */
	private final Connection connection;

	/**
	 * Constructs a SQLiteMovieDAO object and gets the database connection.
	 * 
	 */
	public MovieDAO() {
		Database database = Database.getInstance();
		connection = database.getConnection();
	}

	/**
	 * Returns a list of all movies in the database.
	 * 
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */

	public List<Movie> getAllMovies(int limit) throws SQLException {
		List<Movie> movies = new ArrayList<>();

		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery("select * from movies LIMIT 50");

		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}

		return movies;
	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param id the id of the movie
	 * @return the movie with the specified id
	 * @throws SQLException if a database error occurs
	 */
	public Movie getMovieById(int id) throws SQLException {

		String statement = "select * from movies where id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			return new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"));
		}
		
		// return null if the id does not return a movie.

		return null;

	}

	public List<Person> getStarsByMovieId(int movieId) throws SQLException {
		List<Person> stars = new ArrayList<>();
		String sql = """
        SELECT p.id, p.name, p.birth
        FROM people p
        JOIN stars s ON p.id = s.person_id
        WHERE s.movie_id = ?
    """;

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, movieId);  // Set the movieId parameter
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Person person = new Person(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getInt("birth")
				);
				stars.add(person);
			}
		}

		return stars; // Return the list, which may be empty
	}



	public List<MovieRating> getRatingsByYear(int year, int minVotes, int limit) throws SQLException {
		List<MovieRating> movies = new ArrayList<>();

		String sql = """
        SELECT m.id, m.title, m.year, r.rating, r.votes
        FROM movies m
        JOIN ratings r ON m.id = r.movie_id
        WHERE m.year = ? AND r.votes > ?
        ORDER BY r.rating DESC
        LIMIT ?
    """;

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, year);
			stmt.setInt(2, minVotes);
			stmt.setInt(3, limit);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				movies.add(new MovieRating(
						rs.getInt("id"),
						rs.getString("title"),
						rs.getInt("year"),
						rs.getDouble("rating"),
						rs.getInt("votes")
				));
			}
		}

		return movies;
	}




}
