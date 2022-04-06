package ru.wartis.soa2.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.wartis.soa2.entities.SpaceMarine;
import ru.wartis.soa2.services.JaxbService;
import ru.wartis.soa2.util.AddressUtil;

import javax.xml.bind.JAXBException;

@RequiredArgsConstructor

@Service
public class RestClient {

    private final RestTemplate restTemplate;

    private final AddressUtil addressUtil;

    public void isAlive() {
        final String URI = addressUtil.getUriOfSecondService();

        final ResponseEntity<String> str =
            restTemplate.getForEntity(URI + "/alive", String.class);

        System.out.println(str.getBody());
    }

    public SpaceMarine getSpaceMarineById(Long spaceMarineId) throws JAXBException {
        final String URI = addressUtil.getUriOfSecondService();

        final ResponseEntity<String> strEntity =
            restTemplate.getForEntity(URI + "/spacemarines/" + spaceMarineId, String.class);

        return JaxbService.fromStr(strEntity.getBody(), SpaceMarine.class);
    }

}
