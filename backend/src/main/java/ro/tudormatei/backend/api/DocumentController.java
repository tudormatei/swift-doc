package ro.tudormatei.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.tudormatei.backend.service.AccountService;
import ro.tudormatei.backend.service.DocumentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AccountService accountService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("api/v1/document")
    public ResponseEntity<InputStreamResource> processDocument(@RequestParam("doc") MultipartFile document,  @RequestParam("email") String email) throws IOException {
        String filePath = documentService.process(document, email);
        Path path = Paths.get(filePath);
        File file = path.toFile();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("api/v1/document")
    public ResponseEntity<List<byte[]>> unknownDocumentImages() throws IOException {
        Path currentRelativePath = Paths.get("");
        String savePath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\documents\\unknownDocument\\";
        String imageSaveDirPath = savePath + documentSaveFolder;

        List<byte[]> resource = new ArrayList<>();

        File folder = new File(imageSaveDirPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                //Skip the generated pdf
                if (f.getName().contains(".pdf")) {
                    continue;
                }

                byte[] arr = Files.readAllBytes(Paths.get(f.getPath()));
                resource.add(arr);
            }
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping("/api/v1/auth/register")
    public boolean register(@RequestParam("email") String email, @RequestParam("pass") String pass) {
        boolean status = false;

        status = accountService.createAccount(email, pass);

        return status;
    }

    @PostMapping("/api/v1/auth/login")
    public boolean login(@RequestParam("email") String email, @RequestParam("pass") String pass) {
        boolean status = false;

        status = accountService.loginAccount(email, pass);

        return status;
    }

    @PostMapping("api/v1/auth/updateData")
    public boolean updateUserData(@RequestParam Map<String, String> userData) {
        boolean status = false;

        status = accountService.updateUserData(userData);

        return status;
    }

    @PostMapping("api/v1/auth/getData")
    public ResponseEntity<Map<String, String>> getUserData(@RequestParam("email") String email) {
        Map<String, String> userData = accountService.getUserData(email);
        if (userData == null) {
            return null;
        }

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }
}
