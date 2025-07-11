package com.codequest.model.content;

import java.io.Serializable;

import com.codequest.model.interfaces.Identifiable;

public class Solution implements Identifiable, Serializable {
   private static final long serialVersionUID = 1L;
   private static int idCounter = 1;
   private int id;
   private String title;
   private String author;
   private String explication;
   private int challengeId;

   public Solution(String author, String title,String explication, int challengeId) {
      this.id = idCounter++;
      this.title = title;
      this.author = author;
      this.explication = explication;
      this.challengeId = challengeId;
   }

   // Getters and Setters
   public int getId() {
      return id;
   }
   public String getAuthor() {
      return author;
   }
   public String getExplication() {
      return explication;
   }
   public int getChallengeId() {
      return challengeId;
   }

   public String getTitle() {
      return title;
   }

   public static void setIdCounter(int idCounter) {
      Solution.idCounter = idCounter;
   }
}