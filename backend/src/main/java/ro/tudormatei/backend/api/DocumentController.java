package ro.tudormatei.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.tudormatei.backend.service.DocumentService;
import ro.tudormatei.backend.model.Document;

@RequestMapping("api/v1/document")
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public void processDocument(@RequestBody String s) {
        System.out.println(s);
    }
}
