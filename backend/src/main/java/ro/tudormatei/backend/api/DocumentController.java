package ro.tudormatei.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ro.tudormatei.backend.service.DocumentService;

@RequestMapping("api/v1/document")
@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public String processDocument(@RequestParam("doc") MultipartFile document) {
        String response = documentService.process(document);
        return response;
    }
}
