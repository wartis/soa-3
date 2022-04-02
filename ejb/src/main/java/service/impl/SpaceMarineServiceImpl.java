package service.impl;

import dao.ChapterDAO;
import dao.SpaceMarineDAO;
import exceptions.WrongRequestException;
import model.Chapter;
import model.SpaceMarine;
import model.enums.Weapon;
import model.xmlLists.Marines;
import model.xmlLists.Messages;
import service.SpaceMarineService;
import service.dto.ChapterInGroupElementDto;
import service.dto.GetAllMethodParams;
import service.dto.pagination.PageDto;
import util.FilterSpaceMarineService;
import util.PaginationService;
import util.SortSpaceMarineService;
import util.ValidationService;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Stateless
@Remote(SpaceMarineService.class)
public class SpaceMarineServiceImpl implements SpaceMarineService {

    private final SpaceMarineDAO spaceMarineDao = new SpaceMarineDAO();
    private final ChapterDAO chapterDAO = new ChapterDAO();
    private final FilterSpaceMarineService filterService = new FilterSpaceMarineService();
    private final SortSpaceMarineService sortService = new SortSpaceMarineService();
    private final PaginationService paginationService = new PaginationService();
    private final ValidationService validationService = new ValidationService();


    @Override
    public SpaceMarine getById(Long id) {
        return spaceMarineDao.getSpaceMarine(id).orElse(null);
    }

    @Override
    public Marines getAll(GetAllMethodParams params)
        throws WrongRequestException {

        List<SpaceMarine> spaceMarines = spaceMarineDao.getAllSpaceMarines();

        PageDto page;
        if (spaceMarines == null) {
            page = PageDto.getEmptyPage();
        } else {
            spaceMarines = filterService.handleFiltration(spaceMarines, params.getFiltrationParams());
            spaceMarines = sortService.handleSorting(spaceMarines, params.getSortingParams());
            page = paginationService.handlePagination(spaceMarines, params.getPaginationParams());
        }

        final Marines marines = new Marines();
        marines.setMarines(page.getMarines());
        marines.setPageSize(page.getPageSize());
        marines.setPageNum(page.getCurPage());
        marines.setTotalElements(page.getMarines().size());

        return marines;
    }

    @Override
    public SpaceMarine create(SpaceMarine spaceMarine) throws WrongRequestException {
        spaceMarine.setId(null); //т.к. сущность новая у неё не может быть id
        spaceMarine.resolveEnums();

        final Optional<Messages> optionalMessage = validationService.checkSpaceMarine(spaceMarine);
        if (optionalMessage.isPresent()) {
            throw new WrongRequestException(optionalMessage.get());
        }

        final Chapter chapter = spaceMarine.getChapter();

        if (chapter != null && chapter.getId() != null) {
            final Optional<Chapter> dbChapter = chapterDAO.getChapter(chapter.getId());
            if (dbChapter.isPresent()) {
                spaceMarine.setChapter(dbChapter.get());
            } else {
                Messages messages = new Messages();
                messages.addNewMessage("Не существует chapter'а с указанным id");
                throw new WrongRequestException(messages);
            }
        }

        final long smId = spaceMarineDao.createSpaceMarine(spaceMarine);

        return spaceMarineDao.getSpaceMarine(smId).get();
    }

    @Override
    public void update(Long id, SpaceMarine spaceMarine)
        throws WrongRequestException {

        spaceMarine.setId(id);
        spaceMarine.resolveEnums();

        final Optional<Messages> optionalMessage = validationService.checkSpaceMarine(spaceMarine);
        if (optionalMessage.isPresent()) {
            throw new WrongRequestException(optionalMessage.get());
        }

        final Optional<SpaceMarine> spaceMarineFromDb = spaceMarineDao.getSpaceMarine(spaceMarine.getId());

        if (spaceMarineFromDb.isPresent()) {
            final Chapter chapter = spaceMarine.getChapter();

            if (chapter != null && chapter.getId() != null) {
                final Optional<Chapter> dbChapter = chapterDAO.getChapter(chapter.getId());
                if (dbChapter.isPresent()) {
                    spaceMarine.setChapter(chapter);
                } else {
                    Messages messages = new Messages();
                    messages.addNewMessage("Не существует chapter'а с указанным id");
                    throw new WrongRequestException(messages);
                }
            }


            final SpaceMarine marineDb = spaceMarineFromDb.get();

            spaceMarine.setCreationDate(marineDb.getCreationDate());
            spaceMarineDao.updateSpaceMarine(spaceMarine);
        }
    }

    @Override
    public boolean delete(Long id) {
        //не самое лучшее решение, но spaceMarineDao.deleteSpaceMarine(id) падает если нет id, а не возвращает false
        //почему не ясно и времени разбираться нет(
        if (spaceMarineDao.getSpaceMarine(id).isPresent()) {
            return spaceMarineDao.deleteSpaceMarine(id);
        }

        return false;
    }

    @Override
    public List<ChapterInGroupElementDto> getChaptersInGroup() {
        final Map<Chapter, List<SpaceMarine>> groupedMarines = spaceMarineDao.getAllSpaceMarines().stream()
            .filter(marine -> marine.getChapter() != null)
            .collect(Collectors.groupingBy(SpaceMarine::getChapter));

        return groupedMarines.entrySet().stream()
            .map(el -> new ChapterInGroupElementDto(el.getKey(), el.getValue().size()))
            .collect(Collectors.toList());
    }

    @Override
    public Marines getAllByNameSubstring(String substr) {
        final Marines marines = new Marines();
        final List<SpaceMarine> spaceMarines = spaceMarineDao.getAllSpaceMarines()
            .stream()
            .filter(spaceMarine -> spaceMarine.getName().contains(substr))
            .collect(Collectors.toList());

        marines.setMarines(spaceMarines);

        return marines;
    }

    @Override
    public Marines getAllByWeaponType(Weapon weaponType) {
        final Marines marines = new Marines();
        final List<SpaceMarine> spaceMarines = spaceMarineDao.getAllSpaceMarines()
            .stream()
            .filter(spaceMarine -> spaceMarine.getWeaponType() != null && spaceMarine.getWeaponType() != Weapon.NONE)
            .filter(spaceMarine -> spaceMarine.getWeaponType().ordinal() > weaponType.ordinal())
            .collect(Collectors.toList());

        marines.setMarines(spaceMarines);

        return marines;
    }

    @Override
    public String hello() {
        return "Hello world! I'm Kirill!";
    }
}
