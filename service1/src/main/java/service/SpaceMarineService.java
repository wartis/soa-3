package service;

import exceptions.WrongRequestException;
import model.SpaceMarine;
import model.enums.Weapon;
import model.xmlLists.Marines;
import service.dto.ChapterInGroupElementDto;
import service.dto.GetAllMethodParams;

import javax.ejb.Remote;
import java.util.List;
import java.util.Optional;

@Remote
public interface SpaceMarineService {
    SpaceMarine getById(Long id);
    Marines getAll(GetAllMethodParams params) throws WrongRequestException;
    SpaceMarine create(SpaceMarine spaceMarine) throws WrongRequestException;
    void update(Long id, SpaceMarine spaceMarine) throws WrongRequestException;
    boolean delete(Long id);

    List<ChapterInGroupElementDto> getChaptersInGroup();
    Marines getAllByNameSubstring(String substr);
    Marines getAllByWeaponType(Weapon weaponType);
    String hello();
}
