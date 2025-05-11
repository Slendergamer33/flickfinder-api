package com.flickfinder.model;

/**
 * A person in the movie database.
 * 
 * @TODO: Implement this class
 */
public class Person {

	// - Add your code here: use the MovieDAO.java as an example
	private int id;
    private String name;
    private Integer birth;

    public Person(int id, String name, Integer birth) {
        this.id = id;
        this.name = name;
        this.birth = birth;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth() {
        return birth;
    }

    public void setBirth(Integer birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", birth=" + birth + "]";
    }
}
