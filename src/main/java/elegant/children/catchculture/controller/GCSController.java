package elegant.children.catchculture.controller;

import com.google.cloud.storage.Blob;
import elegant.children.catchculture.dto.GCS.GCSImageDTO;
import elegant.children.catchculture.service.GCS.GCSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GCSController {
    private final GCSService gcsService;

//    @PostMapping(value = "gcs/uploadImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> uploadImageToStorage(
////                                                        @RequestParam("bucketName") String bucketName,
////                                                       @RequestParam("objectName") String objectName,
////                                                        @RequestParam("type") String type,
//                                                       @RequestParam("file") MultipartFile file) throws IOException {
//        String fileName = UUID.randomUUID().toString();
//        String bucketName = "elegant-bucket";
//        String contentType = file.getContentType();
//        GCSImageDTO dto = new GCSImageDTO(bucketName, fileName, file, contentType);
////        GCSImageDTO dto = new GCSImageDTO(bucketName, fileName, file, type, contentType);
//        Blob uploadedFile = gcsService.uploadImageToGCS(dto);
//        return ResponseEntity.ok(uploadedFile.toString());
//    }
}
