package com.example.mushroomapi.mushroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shroom")
public class MushroomController {
    private final MushroomService mushroomService;
    private final MushroomRepository mushroomRepository;

    @Autowired
    public MushroomController(MushroomService mushroomService,
                              MushroomRepository mushroomRepository) {
        this.mushroomService = mushroomService;
        this.mushroomRepository = mushroomRepository;
    }

    @GetMapping
    public List<Mushroom> getMushrooms() {
        return mushroomService.getMushrooms();
    }

    @PostMapping
    public void postMushroom(@RequestBody Mushroom mushroom) {
        mushroomService.postMushroom(mushroom);
    }

    @DeleteMapping(path = "{mushroomId}")
    public void deleteMushroom(@PathVariable("mushroomId") Long mushroomId) {
        mushroomService.deleteMushroom(mushroomId);
    }

    @PutMapping(path ="{mushroomId}")
    public void updateMushroom(@PathVariable("mushroomId") Long mushroomId,
                               @RequestParam(required = false) String name) {
        mushroomService.putMushroom(mushroomId, name);
    }
}
