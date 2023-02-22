//package com.example.mushroomapi.mushroom;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//class MushroomRepositoryTest {
//
//    @Autowired
//    private MushroomRepository underTest;
//
//    @Test
//    void findByName() {
//    }
//
//    @Test
//    void itShouldCheckIfSelectedMushroomExistsByName() {
//        //given
//        Mushroom mushroom = new Mushroom("sss");
//        //when
//        boolean exists = underTest.existsByName(mushroom.getName());
//        //then
//        assertThat(exists).isTrue();
//    }
//}