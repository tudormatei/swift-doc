package ro.tudormatei.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

    public String process(MultipartFile document) {
        System.out.println("Processing the document...");
        System.out.println(document.getName());
        saveDocument(document);
        return "Document sent successfully";
    }

    private void saveDocument(MultipartFile document) {
        try {
            //TODO Save document to disk
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
