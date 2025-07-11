package com.codequest.model.content;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import com.codequest.model.interfaces.Identifiable;

public class Challenge implements Identifiable, Serializable {
   private static int idCounter = 1;
   private int id;
   private String title;
   private String description;
   private String author;
   private List<Integer> solutionsIds = new ArrayList<>();

   public Challenge(String title, String description, String author) {
      this.id = idCounter++;
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

   public List<Integer> getSolutions() {
      return solutionsIds;
   }

   public void addSolution(int solutionId) {
      if (solutionsIds == null) {
         solutionsIds = new ArrayList<>();
      }
      solutionsIds.add(solutionId);
   }

   public static void setIdCounter(int idCounter) {
      Challenge.idCounter = idCounter;
   }
}