package org.example.model;

public class Recipe {
    private Long id;
    private String title;
    private String description;
    private String instructions;
    private Double rating;
    private String image;
    private Integer duration;
    private String created_at;

    public Recipe() {}

    public Recipe(Long id, String title, String description, String instructions, Double rating, String image, Integer duration, String created_at) {
        this.id = id;
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

    public void setRating(Double rating) {
        this.rating = rating;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
