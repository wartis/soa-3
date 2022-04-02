package ru.wartis.soa2.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wartis.soa2.entities.SpaceMarine;
import ru.wartis.soa2.entities.SpaceShip;
import ru.wartis.soa2.exceptions.NotFoundException;
import ru.wartis.soa2.exceptions.ResponseParsingException;
import ru.wartis.soa2.repos.SpaceMarineRepository;
import ru.wartis.soa2.repos.StarshipRepository;
import ru.wartis.soa2.rest.RestClient;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor


@Service
public class StarshipService {

    private final RestClient restClient;

    private final StarshipRepository starshipRepository;

    private final SpaceMarineRepository spaceMarineRepository;

    public void check() {
        restClient.isAlive();
    }

    public void loadSpaceMarineToShip(Long spaceMarineId, Long shipId)  {
        final Optional<SpaceShip> spaceShipOptional = starshipRepository.findById(shipId);
        final SpaceShip spaceShip = spaceShipOptional.orElseThrow(() ->
            new NotFoundException("Starship с id=" + shipId + " не найден!"));

        try {
            final SpaceMarine spaceMarine = restClient.getSpaceMarineById(spaceMarineId);
            spaceMarine.setSpaceShip(spaceShip);
            spaceShip.getSpaceMarine().add(spaceMarine);
            starshipRepository.save(spaceShip);
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new ResponseParsingException("Запрашиваемый сервис прислал невалидное тело. " +
                "Не удалось построить из него объект.");
        }
    }

    public void unloadAllSpaceMarines(Long shipId) {
        final Optional<SpaceShip> spaceShipOptional = starshipRepository.findById(shipId);
        final SpaceShip spaceShip = spaceShipOptional.orElseThrow(() ->
            new NotFoundException("Starship с id=" + shipId + " не найден!"));

        final List<SpaceMarine> all = spaceMarineRepository.findAllBySpaceShip(spaceShip);
        all.forEach(el -> el.setSpaceShip(null));

        spaceMarineRepository.saveAll(all);
    }


}
