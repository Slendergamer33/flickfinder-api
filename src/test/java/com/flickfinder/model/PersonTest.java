package com.flickfinder.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * 
 * TODO: Implement this class
 * 
 */

class PersonTest {

    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person(101, "Christopher Nolan", 1970);
    }

    @Test
    public void testPersonCreated() {
        assertEquals(101, person.getId());
        assertEquals("Christopher Nolan", person.getName());
        assertEquals(1970, person.getBirth());
    }

    @Test
    public void testPersonSetters(){
        person.setId(202);
        person.setName("Quentin Tarantino");
        person.setBirth(1963);
        assertEquals(202, person.getId());
        assertEquals("Quentin Tarantino", person.getName());
        assertEquals(1963, person.getBirth());
    }

    @Test
    public void testPersonNullBirth(){
        person.setBirth(null);
        assertNull(person.getBirth());
    }
}