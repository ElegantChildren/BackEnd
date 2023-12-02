package elegant.children.catchculture.controller;

import elegant.children.catchculture.service.GCS.GCSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @DeleteMapping(value = "gcs/deleteImageByUrl")
    public ResponseEntity<String> deleteImageByUrl(@RequestParam("fileUrl") String fileUrl) {
        try {
            gcsService.deleteFileFromGCSByUrl(fileUrl);
            return ResponseEntity.ok("파일 삭제 성공");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage() + "파일 삭제 실패");
        }
    }

    @DeleteMapping(value = "gcs/deleteImagesByUrlList")
    public ResponseEntity<String> deleteImagesByUrlList(@RequestBody List<String> fileUrlList) {
        try {
            gcsService.deleteFileFromGCSByUrlList(fileUrlList);
            return ResponseEntity.ok("파일 삭제 성공");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage() + "파일 삭제 실패");
        }
    }

    @DeleteMapping(value = "gcs/deleteImageByName")
    public ResponseEntity<String> deleteImageByName(@RequestParam("fileName") String fileName) {
        try {
            gcsService.deleteFileFromGCSByName(fileName);
            return ResponseEntity.ok("파일 삭제 성공");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage() + "파일 삭제 실패");
        }
    }

    @DeleteMapping(value = "gcs/deleteImagesByNameList")
    public ResponseEntity<String> deleteImagesByNameList(@RequestBody List<String> fileNameList) {
        try {
            gcsService.deleteFileFromGCSByNameList(fileNameList);
            return ResponseEntity.ok("파일 삭제 성공");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage() + "파일 삭제 실패");
        }
    }

}
