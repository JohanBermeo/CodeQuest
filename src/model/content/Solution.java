package model.content;

import java.io.Serializable;

import model.interfaces.Identifiable;

public class Solution implements Identifiable, Serializable {
   private static int idCounter = 1;
   private int id;
   private String author;
   private String explication;
   private int challengeId;

   public Solution(String author, String explication, int challengeId) {
      this.id = idCounter++;
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
}