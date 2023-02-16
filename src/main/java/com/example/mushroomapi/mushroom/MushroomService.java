package com.example.mushroomapi.mushroom;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MushroomService {
    private final MushroomRepository mushroomRepository;

    @Autowired
    public MushroomService(MushroomRepository mushroomRepository) {
        this.mushroomRepository = mushroomRepository;
    }

    public List<Mushroom> getMushrooms() {
        return mushroomRepository.findAll();
    }

    public void postMushroom(Mushroom mushroom) {
        Optional<Mushroom> mushroomOptional = mushroomRepository
                .findByName(mushroom.getName());
        if (mushroomOptional.isPresent()) {
            throw new IllegalStateException("Record already exists!");
        }
        mushroomRepository.save(mushroom);
    }

    public void deleteMushroom(Long mushroomId) {
        boolean mushroomExists = mushroomRepository.existsById(mushroomId);
        if (!mushroomExists) {
            throw new IllegalStateException("Mushroom with this number does not exist");
        }
        mushroomRepository.deleteById(mushroomId);

    }

    @Transactional
    public void putMushroom(Long mushroomId, String name) {
        Mushroom mushroom = mushroomRepository.findById(mushroomId)
                .orElseThrow(() ->
                        new IllegalStateException("Mushroom with this number does not exist"));
        if (name != null && name.length() > 0 && !Objects.equals(mushroom.getName(), name)) {
            mushroom.setName(name);
        }
    }
}
