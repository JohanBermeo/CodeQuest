package com.codequest.model.content;

import java.io.Serializable;

import com.codequest.model.interfaces.Identifiable;

public class Challenge implements Identifiable, Serializable {
   private static final long serialVersionUID = 1L;
   private int id;
   private String title;
   private String description;
   private String author;
   private int likes;

   public Challenge(int id, String title, String description, String author) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.author = author;
   }

   // Getters and Setters
   public int getId() {
      return id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getAuthor() {
      return author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public void addLike() {
      likes += 1;
   }

   public int getLikes() {
      return likes;
   }
}