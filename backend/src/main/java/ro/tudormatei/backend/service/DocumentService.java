package ro.tudormatei.backend.service;

import org.springframework.stereotype.Service;
import ro.tudormatei.backend.model.Document;

@Service
public class DocumentService {

    public String process(Document document) {
        System.out.println("Processing the document...");

        return "Fuck you";
    }
}
