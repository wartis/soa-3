package ru.wartis.soa2.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wartis.soa2.entities.SpaceShip;


public interface StarshipRepository extends JpaRepository<SpaceShip, Long> {

}
