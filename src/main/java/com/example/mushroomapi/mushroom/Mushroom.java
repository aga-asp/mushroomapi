package com.example.mushroomapi.mushroom;

import jakarta.persistence.*;

@Entity
@Table
public class Mushroom {
    @Id
    @SequenceGenerator(
            name = "mushroom_sequence",
            sequenceName = "mushroom_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mushroom_sequence"
    )
    private long id;
    private String name;

    public Mushroom() {
    }

    public Mushroom(String name) {

        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Mushroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
