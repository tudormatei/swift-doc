package ro.tudormatei.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.pdf.*;
import ro.tudormatei.backend.model.DataModel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentService {

    public String process(MultipartFile document) {
        System.out.println("Document received!");

        String savePath = saveDocument(document);
        String modifiedDocumentPath = modifyDocument(savePath, document.getOriginalFilename());

        System.out.println(modifiedDocumentPath);

        return modifiedDocumentPath;
    }

    private String saveDocument(MultipartFile document) {
        System.out.println("Document is being saved to local disk...");

        Path currentRelativePath = Paths.get("");
        String savePath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\documents\\";
        savePath = savePath + documentSaveFolder;

        try {
            byte[] bytes = document.getBytes();
            Path path = Paths.get(savePath + document.getOriginalFilename());
            Files.write(path, bytes);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        System.out.println("Document saved successfully!");

        return savePath;
    }

    private String modifyDocument(String documentPath, String name) {
        System.out.println("Document is being modified...");

        String loadPath = documentPath + name;
        String savePath = documentPath + "output.pdf";

        DataModel dataModel = new DataModel();
        Map<String, String> wordsToSearch = dataModel.getWordsToSearch();
        Map<String, String> wordsToReplace = dataModel.getWordsToReplace();

        Document pdfDocument = new Document(loadPath);

        for (Map.Entry<String, String> entry : wordsToSearch.entrySet()) {
            String word = entry.getKey();

            System.out.println("Word that is being searched -> " + word);

            // Create TextAbsorber object to find all instances of the input search phrase
            String expression = word + "\\s?[^\\.|^_]+\\s?[\\.|/|_]+";
            TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber(expression); //Regular expression with the word

            // Set text search option to enable regular expression usage
            TextSearchOptions textSearchOptions = new TextSearchOptions(true);
            textFragmentAbsorber.setTextSearchOptions(textSearchOptions);

            // Accept the absorber for all pages of document
            pdfDocument.getPages().accept(textFragmentAbsorber);

            // Get the extracted text fragments into collection
            TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();

            String key = word;
            if (word.contains("\\")) {
                StringBuilder newWord = new StringBuilder(word);
                int index = newWord.indexOf("\\");
                newWord.deleteCharAt(index);
                word = newWord.toString();
            }

            // Loop through the fragments
            for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentCollection) {
                // Update text and other properties
                TextFragmentState prevTextSate = textFragment.getTextState();

                String replacement = "";
                try {
                    replacement = /*" " + */word + " " + wordsToReplace.get(wordsToSearch.get(key))/* + " "*/;
                    System.out.println("Found word: " + word + " -> replacing it with " + replacement);
                } catch (Exception e) {
                    System.out.println("The replacement was not found -> " + word);
                }

                //Check if field should be completed or is knows
                Color foregroundColor = prevTextSate.getForegroundColor();
                Color backgroundColor = Color.getWhite();

                if (word.equals("nr.")) {
                    replacement = word + " " + "____";

                    foregroundColor = Color.getRed();
                    backgroundColor = Color.getYellow();
                }

                textFragment.setText(replacement);
                textFragment.getTextState().setFont(prevTextSate.getFont());
                textFragment.getTextState().setFontSize(prevTextSate.getFontSize());
                textFragment.getTextState().setForegroundColor(Color.getBlue());
                textFragment.getTextState().setBackgroundColor(backgroundColor);
            }
        }

        // Save the updated PDF file
        pdfDocument.save(savePath);

        System.out.println("Document is finished!");

        return savePath;
    }

}
