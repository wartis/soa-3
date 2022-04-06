package services;

import dao.SpaceMarineDao;
import dao.SpaceShipDao;
import entities.SpaceMarine;
import entities.SpaceShip;
import faults.NotFoundFault;
import faults.ServiceUnavailableFault;
import util.RestClient;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Optional;

public class StarshipHandlerService {

    private final RestClient restClient = new RestClient();

    private final SpaceShipDao starshipRepository = new SpaceShipDao();

    private final SpaceMarineDao spaceMarineRepository = new SpaceMarineDao();

    public void loadSpaceMarineToShip(Long spaceMarineId, Long shipId)
        throws NotFoundException, ServiceUnavailableFault, NotFoundFault {
        final Optional<SpaceShip> spaceShipOptional = starshipRepository.findById(shipId);
        final SpaceShip spaceShip = spaceShipOptional.orElseThrow(() ->
            new NotFoundException("Starship with id=" + shipId + " NOT FOUND!"));

        try {
            final SpaceMarine spaceMarine = restClient.getSpaceMarineById(spaceMarineId);
            spaceMarine.setSpaceShip(spaceShip);
            spaceShip.getSpaceMarine().add(spaceMarine);
            starshipRepository.update(spaceShip);
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new BadRequestException("Requested service sent invalid body. " +
                "The object couldn't be built.");
        }
    }

    public void unloadAllSpaceMarines(Long shipId) throws NotFoundException {
        final Optional<SpaceShip> spaceShipOptional = starshipRepository.findById(shipId);
        final SpaceShip spaceShip = spaceShipOptional.orElseThrow(() ->
            new NotFoundException("Starship with id=" + shipId + " not found!"));

        final List<SpaceMarine> all = spaceMarineRepository.findAllBySpaceShip(spaceShip);
        all.forEach(el -> el.setSpaceShip(null));

        spaceMarineRepository.saveAll(all);
    }


}
