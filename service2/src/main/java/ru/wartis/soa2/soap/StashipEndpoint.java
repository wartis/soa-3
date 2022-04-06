package ru.wartis.soa2.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import ru.wartis.soa2.exceptions.UnloadAllSpaceMarinesRequestFault;
import ru.wartis.soa2.services.StarshipService;
import wartis.LoadSpaceMarineToShipRequest;
import wartis.UnloadAllSpaceMarinesRequest;

@RequiredArgsConstructor

@Endpoint
public class StashipEndpoint {

    private final StarshipService starshipService;

    private static final String NAMESPACE_URI = "http://wartis";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UnloadAllSpaceMarinesRequest")
    public void unloadAllSpaceMarines(@RequestPayload UnloadAllSpaceMarinesRequest unloadAllSpaceMarinesRequest)
        throws UnloadAllSpaceMarinesRequestFault {
        starshipService.unloadAllSpaceMarines(unloadAllSpaceMarinesRequest.getStarshipId());
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoadSpaceMarineToShipRequest")
    public void loadSpaceMarineToShip(@RequestPayload LoadSpaceMarineToShipRequest loadSpaceMarineToShipRequest)
        throws UnloadAllSpaceMarinesRequestFault {
        System.out.println("Запрос пришел на сервер");
        starshipService.loadSpaceMarineToShip(
            loadSpaceMarineToShipRequest.getSpaceMarineId(),
            loadSpaceMarineToShipRequest.getStarshipId()
        );
    }

}
