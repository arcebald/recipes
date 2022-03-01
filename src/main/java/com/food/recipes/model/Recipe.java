package com.food.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String time;

    @Column
    private Integer portions;

    @Column
    private String ingredients;

    @Column
    private String steps;

    @Column
    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Recipe() {
    }

    public Recipe(Long id, String name, String time, Integer portions, String ingredients, String steps, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.portions = portions;
        this.ingredients = ingredients;
        this.steps = steps;
        this.isPublic = isPublic;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getPortions() {
        return portions;
    }

    public void setPortions(Integer portions) {
        this.portions = portions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", portions=" + portions +
                ", ingredients='" + ingredients + '\'' +
                ", steps='" + steps + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}
