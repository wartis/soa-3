package ru.wartis.soa2.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wartis.soa2.entities.SpaceMarine;
import ru.wartis.soa2.entities.SpaceShip;

import java.util.List;

public interface SpaceMarineRepository extends JpaRepository<SpaceMarine, Long> {
    List<SpaceMarine> findAllBySpaceShip(SpaceShip ship);
}
