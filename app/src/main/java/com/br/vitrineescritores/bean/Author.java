package com.br.vitrineescritores.bean;

/**
 * Created by Ronan.lima on 24/04/2018.
 */

public class Author {
    private String firstName;
    private String lastName;
    private Integer id;

    public Author(String firstName, String lastName, Integer id) {
        setFirstName(firstName);
        setLastName(lastName);
        setId(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
