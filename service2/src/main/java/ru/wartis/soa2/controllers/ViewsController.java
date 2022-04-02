package ru.wartis.soa2.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.wartis.soa2.controllers.dto.AddMarineDto;
import ru.wartis.soa2.controllers.dto.MarineDto;
import ru.wartis.soa2.controllers.dto.SpaceShipDto;
import ru.wartis.soa2.repos.SpaceMarineRepository;
import ru.wartis.soa2.repos.StarshipRepository;
import ru.wartis.soa2.services.StarshipService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@RequestMapping("/front")
@Controller
public class ViewsController {

    private final SpaceMarineRepository spaceMarineRepository;

    private final StarshipRepository starshipRepository;

    private final StarshipService starshipService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/ships")
    public ModelAndView ships(Map<String, Object> model) {
        final List<SpaceShipDto> ships = starshipRepository.findAll().stream()
            .map(SpaceShipDto::new)
            .collect(Collectors.toList());
        model.put("ships", ships);

        return new ModelAndView("ships", model);
    }

    @GetMapping("/marines")
    public ModelAndView marines(Map<String, Object> model) {
        final List<MarineDto> ships = spaceMarineRepository.findAll().stream()
            .map(MarineDto::new)
            .collect(Collectors.toList());
        model.put("marines", ships);

        return new ModelAndView("marines", model);
    }

    @GetMapping("/add")
    public ModelAndView add(Map<String, Object> model) {
        model.put("message", "");
        model.put("message1", "");

        return new ModelAndView("manage", model);
    }

    @PostMapping("/add")
    public ModelAndView add(@ModelAttribute AddMarineDto load) {
        final Map<String, Object> model = new HashMap<>();

        try {
            starshipService.loadSpaceMarineToShip(load.getMarineId(), load.getShipId());
            model.put("message", "Успешно!");
        } catch (Exception ex) {
            model.put("message", ex.getMessage());
        }
        model.put("message1", "");


        return new ModelAndView("manage", model);
    }

    @PostMapping("/remove")
    public ModelAndView remove(@ModelAttribute AddMarineDto unload) {
        final Map<String, Object> model = new HashMap<>();

        try {
            starshipService.unloadAllSpaceMarines(unload.getShipId());
            model.put("message1", "Успешно!");
        } catch (Exception ex) {
            model.put("message1", ex.getMessage());
        }
        model.put("message", "");

        return new ModelAndView("manage", model);
    }

}
