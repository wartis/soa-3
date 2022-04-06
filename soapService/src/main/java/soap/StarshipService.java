package soap;

import faults.BadRequestFault;
import faults.NotFoundFault;
import faults.ServiceUnavailableFault;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface StarshipService {

    @WebMethod
    void unloadAllSpaceMarines(
        @WebParam(name = "starshipId") String starshipId
    ) throws NotFoundFault, BadRequestFault;

    @WebMethod
    void loadSpaceMarineToShip(
        @WebParam(name = "starshipId")  String shipId,
        @WebParam(name = "marineId") String marineId
    ) throws NotFoundFault, BadRequestFault, ServiceUnavailableFault;

}
