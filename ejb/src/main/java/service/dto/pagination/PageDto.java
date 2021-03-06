package service.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import model.SpaceMarine;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class PageDto {
    private final List<SpaceMarine> marines;
    private final int totalPages;
    private final int curPage;
    private final int pageSize;



    public static PageDto getEmptyPage() {
        PageDto pageDto = new PageDto(Collections.emptyList(), 0, 0, 0);
        return pageDto;
    }

    public PageDto(List<SpaceMarine> marines, int totalRecords, int curPage, int pageSize) {
        this.marines = marines;
        this.curPage = curPage;
        this.pageSize = pageSize;

        if (totalRecords == pageSize) {
            this.totalPages = 1;
        } else {
            totalPages = totalRecords / pageSize + (totalRecords % pageSize > 0 ? 1 : 0);
        }
    }



}
