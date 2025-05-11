package com.flickfinder.dao;

import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO: Implement this class
 */
class PersonDAOTest {

    private PersonDAO personDAO;

    /**
     * Seeder
     */

    Seeder seeder;
    @BeforeEach
    public void setUp(){
        var url = "jdbc:sqlite::memory:";
        seeder = new Seeder(url);
        Database.getInstance(seeder.getConnection());
        personDAO = new PersonDAO();
    }
    @Test
    public void testGetAllPeople() throws SQLException {
        List<Person> people = personDAO.getAllPeople(1);
        assertNotNull(people);
        assertTrue(people.size() > 0, "The list of people should not be empty");

        // Optionally, check that the first person has expected attributes
        Person firstPerson = people.get(0);
        assertNotNull(firstPerson.getName(), "Person name should not be null");
    }

    @Test
    public void testGetPersonById() throws SQLException {
        Person person = personDAO.getPersonById(1);
        assertNotNull(person);
        assertEquals(person.getId(), 1);
        assertNotNull(person.getName());
    }
}