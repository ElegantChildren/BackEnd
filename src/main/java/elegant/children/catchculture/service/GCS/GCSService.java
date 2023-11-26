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
import java.util.UUID;


@Service
public class GCSService {

    private Storage storage;

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String credentialsFilePath;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final String BASE_URL = "https://storage.googleapis.com/elegant-bucket/";



    private Storage getStorage() throws IOException {
        if (storage == null) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFilePath));
            storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        }
        return storage;
    }

    public Blob uploadImageToGCS2(GCSImageDTO dto) throws IOException {
        Storage storage = getStorage();
        Blob blob = storage.create(
                BlobInfo.newBuilder(dto.getBucketName(), dto.getFileName())
                        .setContentType(dto.getContentType())
                        .build(),
                dto.getFile().getInputStream()
        );
        return blob;
    }

    public String uploadImage(final MultipartFile multipartFile ) {

        final Storage storage;
        try {
            storage = getStorage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final String uuid = UUID.randomUUID().toString();
        final String fileName = getFileName(multipartFile.getOriginalFilename(), uuid);
        final Blob blob = storage.create(BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(multipartFile.getContentType())
                .build());

        return BASE_URL + blob.getName();
    }

    private static String getFileName(final String originalFileName, final String uuid) {
        final StringBuilder sb = new StringBuilder();
        sb.append(uuid).append("_").append(originalFileName);
        final String fileName = sb.toString();
        return fileName;
    }


}
