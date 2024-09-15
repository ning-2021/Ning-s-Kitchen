package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Recipe {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String title;
   private String description;
   // store JSONB as a hashmap later, currently using string
   @Column(columnDefinition = "jsonb")
   private String instructions;
   private Double rating;
   private String image;
   private Integer duration;
   @Column(name = "created_at", nullable = false, updatable = false)
   private LocalDateTime created_at;

   protected Recipe() {}
   public Recipe(String title, String description, String instructions, Double rating, String image, Integer duration, LocalDateTime created_at) {
      this.title = title;
      this.description = description;
      this.instructions = instructions;
      this.rating = rating;
      this.image = image;
      this.duration = duration;
      this.created_at = created_at;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
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

   public String getInstructions() {
      return instructions;
   }

   public void setInstructions(String instructions) {
      this.instructions = instructions;
   }

   public Double getRating() {
      return rating;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public Integer getDuration() {
      return duration;
   }

   public void setDuration(Integer duration) {
      this.duration = duration;
   }

   public LocalDateTime getCreated_at() {
      return created_at;
   }
}

// https://spring.io/guides/gs/accessing-data-jpa
