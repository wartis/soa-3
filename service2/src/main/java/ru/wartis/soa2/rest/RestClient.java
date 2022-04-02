package ru.wartis.soa2.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.wartis.soa2.entities.SpaceMarine;
import ru.wartis.soa2.services.JaxbService;

import javax.xml.bind.JAXBException;

@RequiredArgsConstructor

@Service
public class RestClient {

    @Value("${target.url}")
    private String URI;

    private final RestTemplate restTemplate;

    public void isAlive() {
        final ResponseEntity<String> str =
            restTemplate.getForEntity(URI + "/alive", String.class);

        System.out.println(str.getBody());
    }

    public SpaceMarine getSpaceMarineById(Long spaceMarineId) throws JAXBException {
        final ResponseEntity<String> strEntity =
            restTemplate.getForEntity(URI + "/spacemarines/" + spaceMarineId, String.class);

        return JaxbService.fromStr(strEntity.getBody(), SpaceMarine.class);
    }

}
