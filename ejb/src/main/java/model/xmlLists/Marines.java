package model.xmlLists;

import lombok.Getter;
import lombok.Setter;
import model.SpaceMarine;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


@XmlRootElement
@Getter
@Setter
public class Marines implements Serializable {

    @Element
    private int pageSize;

    @Element
    private int pageNum;

    @Element
    private int totalElements;

    @ElementList
    private List<SpaceMarine> marines;
}
