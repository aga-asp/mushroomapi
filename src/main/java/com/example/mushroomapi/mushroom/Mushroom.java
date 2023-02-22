package com.example.mushroomapi.mushroom;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
//@ToString
@NoArgsConstructor
@Getter
@Setter
public class Mushroom {
    @Id
    @SequenceGenerator(name = "mushroom_sequence",
            sequenceName = "mushroom_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "mushroom_sequence")
    private long id;
    private String name;

    public Mushroom(String name) {
        this.name = name;
    }
}
