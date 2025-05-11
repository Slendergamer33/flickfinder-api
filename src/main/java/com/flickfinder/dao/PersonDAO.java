package com.flickfinder.dao;

import com.flickfinder.util.Database;
import com.flickfinder.model.Person;
import com.flickfinder.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * TODO: Implement this class
 * 
 */
public class PersonDAO {

    private final Connection connection;

    public PersonDAO(){
        Database database = Database.getInstance();
        connection = database.getConnection();
    }
	// for the must have requirements, you will need to implement the following
	// methods:
	// - getAllPeople()
    public List<Person> getAllPeople(int limit) throws SQLException{
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM people LIMIT ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, limit);

        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            people.add(new Person(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("birth")));
        }
        return people;
    }

	// - getPersonById(int id)

    public Person getPersonById(int id) throws SQLException {
        String sql = "SELECT * FROM people WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Person(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("birth")
            );
        }
        return null;
    }
	// you will add further methods for the more advanced tasks; however, ensure your have completed 
	// the must have requirements before you start these.  

    public List<Movie> getMoviesByPersonId(int personId) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = """
        SELECT m.id, m.title, m.year
        FROM movies m
        JOIN stars s ON m.id = s.movie_id
        WHERE s.person_id = ?
        ORDER BY m.year;
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, personId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("year")
                );
                movies.add(movie);
            }
        }

        return movies;
    }





}
