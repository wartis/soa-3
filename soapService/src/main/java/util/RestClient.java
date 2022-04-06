package util;

import com.google.gson.Gson;
import entities.SpaceMarine;
import faults.NotFoundFault;
import faults.ServiceUnavailableFault;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

public class RestClient {

    public SpaceMarine getSpaceMarineById(Long id) throws JAXBException, ServiceUnavailableFault, NotFoundFault {
        final WebTarget target = getTarget();

        if (target == null) {
            throw new ServiceUnavailableFault("Service soa1 is unavailable", 503);
        }

        Response responseLab = target.path("spacemarines").path(String.valueOf(id)).request()
            .accept(MediaType.APPLICATION_XML).get();

        if (responseLab.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new NotFoundFault("Resource not found", 404);
        }

        SpaceMarine spaceMarine = responseLab.readEntity(SpaceMarine.class);

        return spaceMarine;
    }


    private WebTarget getTarget() throws NotFoundFault, ServiceUnavailableFault {
        String consulUrl = System.getenv("CONSUL_URL");
        Client clientConsul = ClientBuilder.newClient();

        Response responseCheckService = clientConsul.target(consulUrl).path("v1").path("agent").path("health")
            .path("service").path("id").path(System.getenv("CONSUL_NAME"))
            .request().accept(MediaType.APPLICATION_JSON).get();


        if (responseCheckService.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new NotFoundFault("Service soa1 NOT FOUND", 404);
        }

        if (responseCheckService.getStatus() == Response.Status.SERVICE_UNAVAILABLE.getStatusCode()) {
            throw new ServiceUnavailableFault("Service soa1 is unavailable", 503);
        }

        Response responseConsul = clientConsul.target(consulUrl).path("v1").path("agent").path("service").path("SERVICE1")
            .request().accept(MediaType.APPLICATION_JSON).get();

        Gson gson = new Gson();
        String sdString = responseConsul.readEntity(String.class);
        ServiceDiscoveryConfiguration serviceDiscoveryConfiguration = gson.fromJson(sdString, ServiceDiscoveryConfiguration.class);

        String BACK_2_URI = "http://" + serviceDiscoveryConfiguration.getAddress() + ":" + serviceDiscoveryConfiguration.getPort() + "/";

        System.out.println("----------------------URL is " + BACK_2_URI + "------------------------------------------------");

        return ClientBuilder.newClient().target(BACK_2_URI);

    }
}
