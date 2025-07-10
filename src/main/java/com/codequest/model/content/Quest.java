package com.codequest.model.content;

import java.io.Serializable;

import com.codequest.model.interfaces.Identifiable;

public class Quest implements Identifiable, Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private String author;

    public Quest(int id, String title, String description, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    @Override
    public int getId() {
        return id;
    }
}
