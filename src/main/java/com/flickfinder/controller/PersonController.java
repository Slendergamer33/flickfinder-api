package com.flickfinder.controller;

import com.flickfinder.dao.PersonDAO;
import com.flickfinder.model.Person;
import com.flickfinder.model.Movie;


import java.sql.SQLException;
import java.util.List;
import io.javalin.http.Context;

public class PersonController {

    private final PersonDAO personDAO;

    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

	// to complete the must-have requirements you need to add the following methods:
	// getAllPeople

    public List<Person> getAllPeople(int limit) throws SQLException {
        return personDAO.getAllPeople(limit);
    }

    public void getAllPeople(Context ctx) {
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
            List<Person> people = personDAO.getAllPeople(limit);
            ctx.json(people);
        } catch (SQLException e) {
            ctx.status(500);
            ctx.result("Database error");
            e.printStackTrace();
        }
    }

    // getPersonById
    public Person getPersonById(int id) throws SQLException {
        return personDAO.getPersonById(id);
    }

    public void getPersonById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Person person = personDAO.getPersonById(id);
            if (person != null) {
                ctx.json(person);
            } else {
                ctx.status(404).result("Person not found");
            }
        } catch (SQLException e) {
            ctx.status(500).result("Database error");
        }
    }
	// you will add further methods for the more advanced tasks; however, ensure your have completed 
	// the must have requirements before you start these.  

    public void getMoviesStarringPerson(Context ctx) {
        try {
            int personId = Integer.parseInt(ctx.pathParam("id"));
            List<Movie> movies = personDAO.getMoviesByPersonId(personId);
            ctx.json(movies);
        } catch (SQLException e) {
            ctx.status(500).result("Database error");
        }
    }

}