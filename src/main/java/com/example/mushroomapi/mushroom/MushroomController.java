package com.example.mushroomapi.mushroom;

import com.example.mushroomapi.weather.AccuWeatherApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/shroom")
public class MushroomController {
    private final MushroomService mushroomService;
    private final MushroomRepository mushroomRepository;

    private final AccuWeatherApiClient accuWeatherApiClient;

    @Autowired
    public MushroomController(MushroomService mushroomService,
                              MushroomRepository mushroomRepository, AccuWeatherApiClient accuWeatherApiClient) {
        this.mushroomService = mushroomService;
        this.mushroomRepository = mushroomRepository;
        this.accuWeatherApiClient = accuWeatherApiClient;
    }


    @GetMapping(path = "{mushroomId}")
    public Mushroom getMushroom(@PathVariable("mushroomId") Long mushroomId) {
        return mushroomService.getRecordById(mushroomId);
    }

    @PostMapping
    public void postMushroom(@RequestBody Mushroom mushroom) {
        mushroomService.postRecord(mushroom);
    }

    @DeleteMapping(path = "{mushroomId}")
    public void deleteMushroom(@PathVariable("mushroomId") Long mushroomId) {
        mushroomService.deleteRecordById(mushroomId);
    }

    @PutMapping(path = "{mushroomId}")
    public void updateMushroom(@PathVariable("mushroomId") Long mushroomId,
                               @RequestParam(required = false) String name) {
        mushroomService.updateRecordById(mushroomId, name);
    }

    @GetMapping("/all")
    public List<Mushroom> getMushrooms() {
        return mushroomService.getAllRecordsFromDataBase();
    }

    @PostMapping("/all")
    public void postMushrooms(@RequestBody List<Mushroom> mushroomList) {
        mushroomService.postRecords(mushroomList);
    }

    @DeleteMapping("/all")
    public void deleteAllMushrooms() {
        mushroomService.deleteAllRecordsFromDatabase();
    }
//    @PutMapping("/all")
//    public void updateAllMushroom(){
//        mushroomService.updateMushrooms();
//    }

    @PostMapping("/forcePostMushrooms")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public void forcePostMushrooms(@RequestBody List<Mushroom> mushroomList) {
        mushroomService.forcePostRecords(mushroomList);
    }

    @GetMapping("weather")
    public void getWeather() throws URISyntaxException, IOException, InterruptedException {
        accuWeatherApiClient.getLocationDto("Kraków");
    }

}
