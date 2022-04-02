package ru.wartis.soa2.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.wartis.soa2.services.StarshipService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/starship")
public class StarshipController {

    private final StarshipService starshipService;

    @GetMapping
    public void check() {
        starshipService.check();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{starshipId}/load/{spaceMarineId}")
    public void loadSpaceMarine(
        @PathVariable Long starshipId,
        @PathVariable Long spaceMarineId
    ) {
        System.out.println("Запрос пришел на сервер");
        starshipService.loadSpaceMarineToShip(spaceMarineId, starshipId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{starshipId}/unload-all")
    public void unloadSpaceMarines(
        @PathVariable Long starshipId
    ) {
        starshipService.unloadAllSpaceMarines(starshipId);
    }

}
