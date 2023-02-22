package com.example.mushroomapi.mushroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MushroomRepository extends JpaRepository<Mushroom, Long> {

    //SELECT * FROM mushroom WHERE name =?
    //@Query("SELECT s FROM Mushroom s WHERE s.name =?1")
    Optional<Mushroom> findByName(String name);

    Boolean existsByName(String name);
}
