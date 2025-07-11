package com.codequest.model.content;

import java.io.Serializable;

import com.codequest.model.interfaces.Identifiable;

public class Quest implements Identifiable, Serializable {
   private static int idCounter = 1;
   private int id;
   private String title;
   private String description;
   private String author;

   public Quest(String title, String description, String author) {
      this.id = idCounter++;
      this.title = title;
      this.description = description;
      this.author = author;
   }

   public int getId() {
      return id;
   }

   public String getTitle() {
      return title;
   }

   public String getDescription() {
      return description;
   }

   public String getAuthor() {
      return author;
   }

   public static void setIdCounter(int idCounter) {
      Quest.idCounter = idCounter;
   }
}
