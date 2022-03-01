package com.food.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories") //create a new table called categories
public class Category {
    @Id// means primary key
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)//equivalent with SERIAL in sql
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "category", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Recipe> recipesList;

    public List<Recipe> getRecipeList() {
        return recipesList;
    }

    public void setRecipesList(List<Recipe> recipesList) {
        this.recipesList = recipesList;
    }

    public Category(){

    }

    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Recipe> getRecipesList() {
        return recipesList;
    }
}
