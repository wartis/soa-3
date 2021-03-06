package service;

import exceptions.WrongRequestException;
import model.enums.Weapon;
import model.xmlLists.Messages;
import service.dto.FiltrationParams;
import service.dto.GetAllMethodParams;
import service.dto.pagination.PaginationParams;
import service.dto.sorting.SortingParams;

import javax.ws.rs.core.MultivaluedMap;

public class ExtractionService {

    public GetAllMethodParams extractParams(MultivaluedMap<String, String> paramsMap) throws WrongRequestException {
        final Messages messages = new Messages();

        final GetAllMethodParams params = new GetAllMethodParams();

        params.getFiltrationParams().setMessages(messages);
        params.getPaginationParams().setMessages(messages);
        params.getSortingParams().setMessages(messages);


        paramsMap.forEach((key, value) -> {
            if (FiltrationParams.FILTRATION_PARAMS.contains(key)) {
                params.getFiltrationParams().setParam(key, value.get(0));
            } else if (SortingParams.SORT_PARAMS.contains(key)) {
                params.getSortingParams().setParam(key, value.get(0));
            } else if (PaginationParams.PAGINATION_PARAMS.contains(key)) {
                params.getPaginationParams().setParam(key, value.get(0));
            } else {
                messages.addNewMessage("Указан несуществующий параметр (" + key + ")");
            }
        });

        if (!messages.getMessages().isEmpty()) {
            throw new WrongRequestException(messages);
        }

        return params;
    }



    public Weapon extractWeaponTypeFromParam(String weaponType) throws WrongRequestException {
        final Messages messages = new Messages();

        if (weaponType == null) {
            messages.addNewMessage("Запрос обязательно должен содержать параметр weaponType");
            throw new WrongRequestException(messages);
        }

        final String weaponTypeStr = weaponType;
        try {
            final Weapon weapon = Weapon.valueOf(weaponTypeStr);
            if (weapon == Weapon.NONE) throw new Exception();
            return weapon;
        } catch (Exception ex) {
            messages.addNewMessage("Не удалось определить значение weaponType. Скорее всего данный weaponType не существует.");
            throw new WrongRequestException(messages);
        }
    }
}
