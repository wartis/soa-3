package ru.wartis.soa2.controllers.dto;

import lombok.Getter;
import lombok.Setter;
import ru.wartis.soa2.entities.SpaceShip;

@Getter
@Setter
public class SpaceShipDto {

    private Long id;
    private String name;
    private int marinesNum;

    public SpaceShipDto(SpaceShip ship) {
        id = ship.getId();
        name = ship.getName();
        marinesNum = ship.getSpaceMarine().size();
    }

}
