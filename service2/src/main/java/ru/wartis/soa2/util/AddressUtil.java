package ru.wartis.soa2.util;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.wartis.soa2.rest.RestClient;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequiredArgsConstructor

@Service
public final class AddressUtil {

    private final RestTemplate restTemplate;

    public String getUriOfSecondService() {
        String consulUrl = System.getenv("CONSUL_URL");

        final ResponseEntity<String> response =
            restTemplate.getForEntity(consulUrl + "/v1/agent/health/service/id/" + System.getenv("CONSUL_NAME"),
                String.class);


        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NotFoundException("Service soa1 NOT FOUND");
        }

        if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            throw new ServiceUnavailableException("Service soa1 is unavailable");
        }

        final ResponseEntity<String> responseConsul =
            restTemplate.getForEntity(consulUrl + "/v1/agent/service/" + System.getenv("CONSUL_NAME"),
                String.class);


        Gson gson = new Gson();
        String sdString = responseConsul.getBody();
        ServiceDiscoveryConfiguration serviceDiscoveryConfiguration = gson.fromJson(sdString, ServiceDiscoveryConfiguration.class);

        return "https://" + serviceDiscoveryConfiguration.getAddress() + ":" + serviceDiscoveryConfiguration.getPort() + "/";

    }


}
