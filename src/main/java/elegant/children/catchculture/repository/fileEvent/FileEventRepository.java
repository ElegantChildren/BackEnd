package elegant.children.catchculture.repository.fileEvent;

import elegant.children.catchculture.entity.fileEvent.FileEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileEventRepository extends JpaRepository<FileEvent, Integer> {



}
