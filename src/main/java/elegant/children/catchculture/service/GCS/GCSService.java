package elegant.children.catchculture.service.GCS;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import elegant.children.catchculture.dto.GCS.GCSImageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;


@Service
public class GCSService {

    private Storage storage;

    @Value("${spring.cloud.gcp.storage.credentials.file-path}")
    private String credentialsFilePath;

    private Storage getStorage() throws IOException {
        if (storage == null) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFilePath));
            storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        }
        return storage;
    }

    public Blob uploadImageToGCS(GCSImageDTO dto) throws IOException {
        Storage storage = getStorage();
        Blob blob = storage.create(
                BlobInfo.newBuilder(dto.getBucketName(), dto.getFileName())
                        .setContentType(dto.getContentType())
                        .build(),
                dto.getFile().getInputStream()
        );
        return blob;
    }


}
