import util.CheckServiceConfiguration;
import util.ServiceDiscoveryConfiguration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationPath("")
public class App extends Application {

    public App() throws Exception {
        super();

        Client client = ClientBuilder.newBuilder().build();
        String consulUrl = System.getenv("CONSUL_URL");

        final int port = Integer.parseInt(System.getenv("SERVICE_PORT"));

        ServiceDiscoveryConfiguration configuration = new ServiceDiscoveryConfiguration();
        configuration.setId(System.getenv("CONSUL_NAME"));
        configuration.setName(System.getenv("CONSUL_NAME"));
        configuration.setAddress(System.getenv("SERVICE_ADDRESS"));
        configuration.setPort(port);

        CheckServiceConfiguration checkServiceConfiguration = new CheckServiceConfiguration();
        checkServiceConfiguration.setName("check " + System.getenv("CONSUL_NAME"));
        checkServiceConfiguration.setTcp("localhost:" + port);
        checkServiceConfiguration.setInterval(System.getenv("CONSUL_CHECK_INTERVAL"));
        checkServiceConfiguration.setStatus("critical");

        configuration.setCheck(checkServiceConfiguration);

        Response response = client
            .target(consulUrl + "/v1/agent/service/register")
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.entity(configuration, MediaType.APPLICATION_JSON));

        System.out.println(response.getStatus());

        if (response.getStatus() != 200) {
            throw new Exception("Failed to register service in Service discovery");
        }
    }
}
