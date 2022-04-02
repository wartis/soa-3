package ru.wartis.soa2.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "space_ship")

@NoArgsConstructor
@Getter
@Setter
public class SpaceShip {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "spaceShip", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SpaceMarine> spaceMarine;

}
