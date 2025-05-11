package com.flickfinder.controller;

import com.flickfinder.dao.PersonDAO;
import com.flickfinder.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TODO: Implement this class
 */
class PersonControllerTest {
    private PersonDAO personDAO;
    private PersonController personController;

    @BeforeEach
    public void setUp() {
        personDAO = mock(PersonDAO.class);
        personController = new PersonController(personDAO);
    }

    @Test
    public void testGetAllPeople() throws SQLException {
        List<Person> mockPeople = Arrays.asList(
                new Person(1, "Person One", 1980),
                new Person(2, "Person Two", 1990)
        );

        when(personDAO.getAllPeople(1)).thenReturn(mockPeople);

        List<Person> result = personController.getAllPeople(1);
        assertEquals(2, result.size());
        verify(personDAO, times(1)).getAllPeople(1);
    }

    @Test
    public void testGetPersonById() throws SQLException {
        Person mockPerson = new Person(1, "Person One", 1980);
        when(personDAO.getPersonById(1)).thenReturn(mockPerson);

        Person result = personController.getPersonById(1);
        assertNotNull(result);
        assertEquals("Person One", result.getName());
        verify(personDAO, times(1)).getPersonById(1);
    }
}