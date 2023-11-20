package elegant.children.catchculture.dto.GCS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class GCSImageDTO {
        private String bucketName;
        private String fileName;
        private MultipartFile file;
        private String type;
        private String contentType;
}
