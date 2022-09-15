package com.disneydemo.demo.model;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String img;

    private String name;

    private int age;

    private double weight;

    @Lob
    private String story;

    @OneToMany(mappedBy="character", orphanRemoval = true, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CharacterMovie> characterMovies = new LinkedList<CharacterMovie>();


    public Character() {
        this.img = "";
        this.name = "";
        this.age = 0;
        this.weight = 0.0;
        this.story = "";
    }

    public Character(String img, String name, int age, double weight, String story
            //, List<CharacterMovie> characterSeries
    )
    {
        this.img = img;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.story = story;
   //     this.characterSeries = characterSeries;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

   public List<CharacterMovie> getCharacterMovies() {
        return characterMovies;
    }

    @JsonIgnore
    public void setCharacterMovies(List<CharacterMovie> characterMovies) {
        this.characterMovies = characterMovies;
    }

    public void addCharacterMovie(CharacterMovie characterMovie) {
        characterMovies.add(characterMovie);
    }

    public void removeCharacterMovie(CharacterMovie characterMovie) {
        characterMovies.remove(characterMovie);
    }
}
