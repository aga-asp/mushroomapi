package com.example.mushroomapi.mushroom;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MushroomService {
    private final MushroomRepository mushroomRepository;

    @Autowired
    public MushroomService(MushroomRepository mushroomRepository) {
        this.mushroomRepository = mushroomRepository;
    }

    public List<Mushroom> getAllRecordsFromDataBase() {
        return mushroomRepository.findAll();
    }

    public void forcePostRecords(List<Mushroom> mushroomList) {
        List<Mushroom> newList = mushroomList
                .stream()
                .filter(mushroom -> !(mushroomRepository.existsByName(mushroom.getName())))
                .toList();
        List<String> duplicatesNames = getListOfNamesOfDuplicates(mushroomList);
        mushroomRepository.saveAll(newList);
        if (!mushroomList.equals(newList)) {
            throw new DuplicatedMushroomException("List posted but duplicated records where not posted: " + duplicatesNames);
        }
    }

    public void postRecords(List<Mushroom> mushroomList) {
        boolean duplicatesCheck = mushroomList.stream()
                .anyMatch(mushroom -> mushroomRepository.existsByName(mushroom.getName()));
        if (duplicatesCheck) {
            List<String> duplicatesNames = getListOfNamesOfDuplicates(mushroomList);
            throw new DuplicatedMushroomException("Database contains duplicates: " + duplicatesNames);
        }
        mushroomRepository.saveAll(mushroomList);
    }

    public List<String> getListOfNamesOfDuplicates(List<Mushroom> mushroomList) {
        return mushroomList
                .stream()
                .map(Mushroom::getName)
                .filter(mushroomRepository::existsByName)
                .collect(Collectors.toList());
    }


    public void postRecord(Mushroom mushroom) {
        Optional<Mushroom> mushroomOptional = mushroomRepository
                .findByName(mushroom.getName());
        if (mushroomOptional.isPresent()) {
            throw new DuplicatedMushroomException("Record already exists!");
        }
        mushroomRepository.save(mushroom);
    }

    public void deleteRecordById(Long mushroomId) {
        boolean mushroomExists = mushroomRepository.existsById(mushroomId);
        if (!mushroomExists) {
            throw new MissingMushroomException("Record with this ID does not exist");
        }
        mushroomRepository.deleteById(mushroomId);

    }

    @Transactional
    public void updateRecordById(Long mushroomId, String name) {
        Mushroom mushroom = mushroomRepository.findById(mushroomId)
                .orElseThrow(() ->
                        new MissingMushroomException("Record with this ID does not exist"));
        if (name != null && name.length() > 0 && !Objects.equals(mushroom.getName(), name)) {
            mushroom.setName(name);
        }else {
            throw new IllegalArgumentException("Value is incorrect, name not changed");
        }
    }

    public Mushroom getRecordById(Long mushroomId) {
        return mushroomRepository.findById(mushroomId)
                .orElseThrow(() ->
                        new MissingMushroomException("Record with this ID does not exist"));
    }

    public void deleteAllRecordsFromDatabase() {
        mushroomRepository.deleteAll();
    }
}
