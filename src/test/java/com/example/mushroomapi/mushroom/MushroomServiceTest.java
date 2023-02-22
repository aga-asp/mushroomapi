package com.example.mushroomapi.mushroom;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MushroomServiceTest {
    MushroomRepository mushroomRepository = mock(MushroomRepository.class);
    MushroomService mushroomService = new MushroomService(mushroomRepository);

    @Test
    void shouldGetAllRecordsFromDataBase() {
        //given
        List<Mushroom> expected = List.of(new Mushroom("ObjectOne"), new Mushroom("ObjectTwo"));
        when(mushroomRepository.findAll()).thenReturn(expected);
        //when
        List<Mushroom> actual = mushroomService.getAllRecordsFromDataBase();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void shouldForcePostRecordsIfThereAreNoDuplicates() {
        //given
        List<Mushroom> inputList = List.of(new Mushroom("ObjectOne"), new Mushroom("ObjectTwo"));
        when(mushroomRepository.existsByName("ObjectOne")).thenReturn(false);
        when(mushroomRepository.existsByName("ObjectTwo")).thenReturn(false);

        //when
        mushroomService.forcePostRecords(inputList);

        //then
        verify(mushroomRepository).saveAll(inputList);
    }

    @Test
    void shouldForcePostRecordsIfThereAreDuplicatesAndThrowInformationAboutOmittedRecords() {
        //given
        Mushroom mushroomTwo = new Mushroom("ObjectTwo");
        Mushroom mushroomOne = new Mushroom("ObjectOne");
        List<Mushroom> inputList = List.of(mushroomOne, mushroomTwo);
        when(mushroomRepository.existsByName("ObjectOne")).thenReturn(false);
        when(mushroomRepository.existsByName("ObjectTwo")).thenReturn(true);

        //when-then
        assertThrows(DuplicatedMushroomException.class, () -> mushroomService.forcePostRecords(inputList));
        verify(mushroomRepository).saveAll(List.of(mushroomOne));
    }

    @Test
    void shouldPostRecordsIfThereIsNoDuplicates() {
        //given
        Mushroom mushroomTwo = new Mushroom("ObjectTwo");
        Mushroom mushroomOne = new Mushroom("ObjectOne");
        List<Mushroom> inputList = List.of(mushroomOne, mushroomTwo);
        when(mushroomRepository.existsByName("ObjectOne")).thenReturn(false);
        when(mushroomRepository.existsByName("ObjectTwo")).thenReturn(false);
        //when
        mushroomService.postRecords(inputList);
        //then
        verify(mushroomRepository).saveAll(inputList);
    }

    @Test
    void shouldThrowExceptionIfPostingDuplicates() {
        //given
        Mushroom mushroomTwo = new Mushroom("ObjectTwo");
        Mushroom mushroomOne = new Mushroom("ObjectOne");
        List<Mushroom> inputList = List.of(mushroomOne, mushroomTwo);
        when(mushroomRepository.existsByName("ObjectOne")).thenReturn(false);
        when(mushroomRepository.existsByName("ObjectTwo")).thenReturn(true);
        //when-then
        assertThrows(DuplicatedMushroomException.class, () -> mushroomService.forcePostRecords(inputList));
    }

    @Test
    void shouldGetListOfNamesIfDuplicatesExist() {
        //given
        Mushroom mushroomTwo = new Mushroom("ObjectTwo");
        Mushroom mushroomOne = new Mushroom("ObjectOne");
        List<String> expectedResult = List.of(mushroomOne.getName(), mushroomTwo.getName());
        List<Mushroom> inputList = List.of(mushroomOne, mushroomTwo);
        when(mushroomRepository.existsByName("ObjectOne")).thenReturn(true);
        when(mushroomRepository.existsByName("ObjectTwo")).thenReturn(true);
        //when
        List<String> outputList = mushroomService.getListOfNamesOfDuplicates(inputList);
        //then
        assertEquals(expectedResult, outputList);
    }

    @Test
    void shouldReturnEmptyListIfThereAreNoDuplicates() {
        //given
        Mushroom mushroomTwo = new Mushroom("ObjectTwo");
        Mushroom mushroomOne = new Mushroom("ObjectOne");
        List<String> expectedResult = List.of(mushroomOne.getName(), mushroomTwo.getName());
        List<Mushroom> inputList = List.of(mushroomOne, mushroomTwo);
        when(mushroomRepository.existsByName("ObjectOne")).thenReturn(false);
        when(mushroomRepository.existsByName("ObjectTwo")).thenReturn(false);
        //when
        List<String> outputList = mushroomService.getListOfNamesOfDuplicates(inputList);
        //then
        assertNotEquals(outputList, expectedResult);

    }

    @Test
    void shouldPostRecordIfDuplicateDoesNotExists() {
        //given
        Mushroom mushroom = new Mushroom("Mushroom");
        Mushroom mushroomOptional = new Mushroom("Optional");
        when(mushroomRepository.findByName("Optional")).thenReturn(Optional.of(mushroomOptional));
        //when
        mushroomService.postRecord(mushroom);
        //then
        verify(mushroomRepository).save(mushroom);

    }

    @Test
    void shouldThrowExceptionPostingRecordIfDuplicateExist() {
        //given
        Mushroom mushroom = new Mushroom("Mushroom");
        when(mushroomRepository.findByName("Mushroom")).thenReturn(Optional.of(mushroom));
        //when-then
        assertThrows(DuplicatedMushroomException.class, () -> mushroomService.postRecord(mushroom));
    }

    @Test
    void shouldDeleteRecordByIdIfIdExists() {
        //given
        Mushroom mushroom = new Mushroom("Mushroom");
        when(mushroomRepository.existsById(mushroom.getId())).thenReturn(true);
        //when
        mushroomService.deleteRecordById(mushroom.getId());
        //then
        verify(mushroomRepository).deleteById(mushroom.getId());
    }

    @Test
    void shouldThrowExceptionWhenDeletingRecordIfIdDoNotExist() {
        //given
        Mushroom mushroom = new Mushroom("Mushroom");
        when(mushroomRepository.existsById(mushroom.getId())).thenReturn(false);
        //when-then
        assertThrows(MissingMushroomException.class, () -> mushroomService.deleteRecordById(mushroom.getId()));
    }

    @Test
    void shouldUpdateRecordByIdIfRecordExists() {
        //given
        Mushroom mushroom = new Mushroom("Mushroom");
        String newName = "New Name";
        when(mushroomRepository.findById(mushroom.getId())).thenReturn(Optional.of(mushroom));
        //when
        mushroomService.updateRecordById(mushroom.getId(), newName);
        //then
        assertEquals(mushroom.getName(), newName);
    }

    @Test
    void shouldThrowExceptionIfUpdatedValueIsIncorrect() {
        //given
        Mushroom mushroom = new Mushroom("Mushroom");
        String newName = "";
        when(mushroomRepository.findById(mushroom.getId())).thenReturn(Optional.of(mushroom));
        //when-then
        assertThrows(IllegalArgumentException.class, () -> mushroomService.updateRecordById(mushroom.getId(), newName));
    }

    @Test
    void shouldThrowExceptionUpdatingRecordIfItDoesNotExist() {
        //given
        Mushroom mushroom = new Mushroom("Mushroom");
        String newName = "New Name";
        when(mushroomRepository.findById(mushroom.getId())).thenReturn(Optional.empty());
        //when-then
        assertThrows(MissingMushroomException.class, () -> mushroomService.updateRecordById(mushroom.getId(), newName));
    }

    @Test
    void shouldGetRecordByIdIfIdExists() {
        //given
        Mushroom mushroom = new Mushroom("Mushroom");
        when(mushroomRepository.findById(mushroom.getId())).thenReturn(Optional.of(mushroom));
        //when
        mushroomService.getRecordById(mushroom.getId());
        //then
        verify(mushroomRepository).findById(mushroom.getId());
    }

    @Test
    void shouldThrowExceptionGettingRecordByIdIfIdNotExist() {
        //given
        Mushroom mushroom = new Mushroom("Mushroom");
        when(mushroomRepository.findById(mushroom.getId())).thenReturn(Optional.empty());
        //when-then
        assertThrows(MissingMushroomException.class, () -> mushroomService.getRecordById(mushroom.getId()));
    }

    @Test
    void shouldDeleteAllRecordsFromDatabase() {
        //when
        mushroomService.deleteAllRecordsFromDatabase();
        //then
        verify(mushroomRepository).deleteAll();
    }
}