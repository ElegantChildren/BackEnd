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
import java.util.ArrayList;
import java.util.List;
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

    public String uploadImageToGCS(GCSImageDTO dto) throws IOException {
        Storage storage = getStorage();
        Blob blob = storage.create(
                BlobInfo.newBuilder(dto.getBucketName(), dto.getFileName())
                        .setContentType(dto.getContentType())
                        .build(),
                dto.getFile().getInputStream()
        );
        return BASE_URL + blob.getName();
    }

    public String uploadImage(final MultipartFile multipartFile ) {

        final Storage storage;
        final Blob blob;
        final String uuid = UUID.randomUUID().toString();
        final String fileName = getFileName(multipartFile.getOriginalFilename(), uuid);
        try {
            storage = getStorage();
            blob = storage.create(
                    BlobInfo.newBuilder(bucketName, fileName)
                    .setContentType(multipartFile.getContentType())
                    .build(),
                    multipartFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return BASE_URL + blob.getName();
    }


    public List<String> uploadImages(final List<MultipartFile> multipartFiles ) {

        final Storage storage;
        List<String> URL_list = new ArrayList<>();

        try {
            storage = getStorage();
            for (MultipartFile multipartFile : multipartFiles) {
                Blob blob;
                String uuid = UUID.randomUUID().toString();
                String fileName = getFileName(multipartFile.getOriginalFilename(), uuid);
                blob = storage.create(
                        BlobInfo.newBuilder(bucketName, fileName)
                                .setContentType(multipartFile.getContentType())
                                .build(),
                        multipartFile.getInputStream());
                URL_list.add(BASE_URL + blob.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return URL_list;
    }

    private static String getFileName(final String originalFileName, final String uuid) {
        return uuid + "_" + originalFileName;
    }

    public void deleteFileFromGCSByName(String fileName) throws IOException {
        Storage storage = getStorage();
        storage.delete(bucketName, fileName);
    }

    public void deleteFileFromGCSByNameList(List<String> fileNameList) throws IOException {
        Storage storage = getStorage();
        for (String fileName : fileNameList) {
            storage.delete(bucketName, fileName);
        }

    }

    public void deleteFileFromGCSByUrl(String fileUrl) throws IOException {
        String fileName = extractFileNameFromUrl(fileUrl);
        Storage storage = getStorage();
        storage.delete(bucketName, fileName);
    }

    public void deleteFileFromGCSByUrlList(List<String> fileUrlList) throws IOException {
        Storage storage = getStorage();
        for (String fileUrl : fileUrlList) {
            String fileName = extractFileNameFromUrl(fileUrl);
            storage.delete(bucketName, fileName);
        }
    }

    private String extractFileNameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
    }

}
