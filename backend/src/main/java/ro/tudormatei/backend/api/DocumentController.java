package ro.tudormatei.backend.api;

import com.aspose.pdf.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ro.tudormatei.backend.service.DocumentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/v1/document")
@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<InputStreamResource> processDocument(@RequestParam("doc") MultipartFile document) throws IOException {
        String filePath = documentService.process(document);
        Path path = Paths.get(filePath);
        File file = path.toFile();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<byte[]>> unknownDocumentImages() throws IOException {
        Path currentRelativePath = Paths.get("");
        String savePath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\documents\\unknownDocument\\";
        String imageSaveDirPath = savePath + documentSaveFolder;

        List<byte[]> resource = new ArrayList<>();

        File folder = new File(imageSaveDirPath);
        File[] files = folder.listFiles();
        if(files!=null) {
            for(File f: files) {
                //Skip the generated pdf
                if(f.getName().contains(".pdf")){
                    continue;
                }

                byte[] arr = Files.readAllBytes(Paths.get(f.getPath()));
                resource.add(arr);
            }
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
