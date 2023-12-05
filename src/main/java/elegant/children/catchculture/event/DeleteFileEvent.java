package elegant.children.catchculture.event;

import elegant.children.catchculture.entity.fileEvent.FileEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeleteFileEvent {

    private List<FileEvent> fileEventList;
}
