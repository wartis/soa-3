package soap.impl;

import faults.BadRequestFault;
import faults.NotFoundFault;
import faults.ServiceUnavailableFault;
import services.StarshipHandlerService;
import soap.StarshipService;

import javax.jws.WebService;

@WebService(endpointInterface = "soap.StarshipService")
public class StarshipServiceImpl implements StarshipService {

    private final StarshipHandlerService starshipHandlerService = new StarshipHandlerService();

    @Override
    public void unloadAllSpaceMarines(String shipId) throws NotFoundFault, BadRequestFault {
        Long ship;

        try {
            ship = Long.parseLong(shipId);
        } catch (NumberFormatException nfe) {
            throw new BadRequestFault("Passed value isn't LONG.", 404);
        }

        starshipHandlerService.unloadAllSpaceMarines(ship);
    }

    @Override
    public void loadSpaceMarineToShip(String shipId, String marineId)
        throws NotFoundFault, BadRequestFault, ServiceUnavailableFault {
        Long ship, marine;

        try {
            ship = Long.parseLong(shipId);
            marine = Long.parseLong(marineId);
        } catch (NumberFormatException nfe) {
            throw new BadRequestFault("Passed value isn't LONG.", 404);
        }

        starshipHandlerService.loadSpaceMarineToShip(marine, ship);
    }
}
