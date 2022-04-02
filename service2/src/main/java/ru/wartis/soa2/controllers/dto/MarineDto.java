package ru.wartis.soa2.controllers.dto;

import lombok.Getter;
import lombok.Setter;
import ru.wartis.soa2.entities.SpaceMarine;

@Getter
@Setter
public class MarineDto {

    private String name;
    private Long id;
    private String shipName;

    public MarineDto(SpaceMarine marine) {
        name = marine.getName();
        id = marine.getId();
        shipName = marine.getSpaceShip() == null ? "Нет корабля" : marine.getSpaceShip().getName();
    }
}
