package elegant.children.catchculture.controller;

import elegant.children.catchculture.service.GCS.GCSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GCSController {
    private final GCSService gcsService;

    @PostMapping(value = "gcs/uploadImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImageToStorage(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadedFile = gcsService.uploadImage(file);
        return ResponseEntity.ok(uploadedFile);
    }


    @PostMapping(value = "gcs/uploadImages",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadImagesToStorage(
            @RequestParam("fileList") List<MultipartFile> fileList) throws IOException {
        List<String> uploadedFile = gcsService.uploadImages(fileList);
        return ResponseEntity.ok(uploadedFile);
    }
}
