package model.xmlLists;

import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.ElementList;
import service.dto.ChapterInGroupElementDto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@Getter
@Setter
public class ChaptersInGroup implements Serializable {
    @ElementList
    private List<ChapterInGroupElementDto> group;
}
