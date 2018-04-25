package com.br.vitrineescritores.bean;

/**
 * Created by rlima on 25/04/18.
 */

public class Book {
    private String title;
    private Integer id;
    private Integer authorId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
}
