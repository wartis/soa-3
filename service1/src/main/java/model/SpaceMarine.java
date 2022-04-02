package model;


import lombok.Getter;
import lombok.Setter;
import model.enums.AstartesCategory;
import model.enums.MeleeWeapon;
import model.enums.Weapon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SpaceMarine")

@Getter
@Setter
public class SpaceMarine implements Serializable {

    @XmlElement
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    private Coordinates coordinates;

    private transient LocalDateTime creationDate = LocalDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @XmlElement
    private Integer health; //Поле может быть null, Значение поля должно быть больше 0

    @XmlElement
    private AstartesCategory category;

    @XmlElement
    private Weapon weaponType; //Поле может быть null

    @XmlElement
    private MeleeWeapon meleeWeapon; //Поле может быть null

    @XmlElement
    private Chapter chapter; //Поле может быть null
}
