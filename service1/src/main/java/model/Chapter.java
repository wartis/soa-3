package model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "chapter")

@Getter
@Setter
public class Chapter implements Serializable {
    @XmlElement
    private Long id;

    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    private String parentLegion;

    @XmlElement
    private int marinesCount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000

    @XmlElement
    private String world; //Поле может быть null

}
