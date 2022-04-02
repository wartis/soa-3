package service.dto;

import lombok.Getter;
import lombok.Setter;
import service.dto.pagination.PaginationParams;
import service.dto.sorting.SortingParams;

import java.io.Serializable;

@Getter
@Setter
public class GetAllMethodParams implements Serializable {
    private FiltrationParams filtrationParams = new FiltrationParams();

    private SortingParams sortingParams = new SortingParams();

    private PaginationParams paginationParams = new PaginationParams();
}
