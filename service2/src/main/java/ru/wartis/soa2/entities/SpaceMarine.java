package ru.wartis.soa2.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SpaceMarine")

@Entity
@Table(name = "space_marine")

@Getter
@Setter
public class SpaceMarine {

    @XmlElement
    @Id
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne
    @JoinColumn(name = "space_ship_id")
    private SpaceShip spaceShip;

}
