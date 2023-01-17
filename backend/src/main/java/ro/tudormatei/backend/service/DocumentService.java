package ro.tudormatei.backend.service;

import com.aspose.pdf.devices.PngDevice;
import com.aspose.pdf.devices.Resolution;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.pdf.*;
import ro.tudormatei.backend.model.DataModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;

@Service
public class DocumentService {

    public String process(MultipartFile document, String email) {
        System.out.println("Document received!");

        String savePath = saveDocument(document);
        String modifiedDocumentPath = modifyDocument(savePath, document.getOriginalFilename(), email);

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

    private String modifyDocument(String documentPath, String name, String email) {
        System.out.println("User that requested document completion is: " + email);
        System.out.println("Document is being modified...");

        String loadPath = documentPath + name;
        String savePath = documentPath + "output.pdf";

        DataModel dataModel = new DataModel(email);
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

                String replacementKey = wordsToSearch.get(key);
                if(replacementKey.equals("date")) {
                    replacement = word + " " + LocalDate.now();
                }
                else if(wordsToSearch.get(key).equals("fullName")) {
                    String fullName = wordsToReplace.get("fname") + " " + wordsToReplace.get("lname");
                    replacement = word + " " + fullName;
                }
                else if(replacementKey.equals("streetNr")) {
                    replacement = textFragment.getText();
                }

                //Check if field should be completed or is knows
                Color foregroundColor = prevTextSate.getForegroundColor();
                Color backgroundColor = Color.getWhite();

                textFragment.setText(replacement);
                textFragment.getTextState().setFont(prevTextSate.getFont());
                textFragment.getTextState().setFontSize(prevTextSate.getFontSize());
                textFragment.getTextState().setForegroundColor(foregroundColor);
                textFragment.getTextState().setBackgroundColor(backgroundColor);
            }
        }

        // Save the updated PDF file
        pdfDocument.save(savePath);

        System.out.println("Document is completed!");

        generateUnknownDocument(savePath);

        return savePath;
    }

    private void generateUnknownDocument(String path) {
        Document pdfDocument = new Document(path);

        Path currentRelativePath = Paths.get("");
        String savePath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\documents\\unknownDocument\\";
        savePath = savePath + documentSaveFolder + "output.pdf";
        System.out.println("The save path for the unknown document is... " + savePath);

        String expression = "([\\.])\\1\\1+";
        TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber(expression); //Regular expression with the word

        // Set text search option to enable regular expression usage
        TextSearchOptions textSearchOptions = new TextSearchOptions(true);
        textFragmentAbsorber.setTextSearchOptions(textSearchOptions);

        // Accept the absorber for all pages of document
        pdfDocument.getPages().accept(textFragmentAbsorber);

        // Get the extracted text fragments into collection
        TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();

        // Loop through the fragments
        for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentCollection) {
            // Update text and other properties
            TextFragmentState prevTextSate = textFragment.getTextState();
            String replacement = textFragment.getText();

            //Check if field should be completed or is knows
            Color foregroundColor = prevTextSate.getForegroundColor();
            Color backgroundColor = Color.getYellow();

            textFragment.setText(replacement);
            textFragment.getTextState().setFont(prevTextSate.getFont());
            textFragment.getTextState().setFontSize(prevTextSate.getFontSize());
            textFragment.getTextState().setForegroundColor(foregroundColor);
            textFragment.getTextState().setBackgroundColor(backgroundColor);
        }


        // Save the updated PDF file
        pdfDocument.save(savePath);

        generateUnknownDocumentImages(savePath);
    }

    private  void generateUnknownDocumentImages(String path) {
        System.out.println("Started generating images for the pdf");

        Document pdfDocument = new Document(path);

        Path currentRelativePath = Paths.get("");
        String savePath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\documents\\unknownDocument\\";
        String imageSaveDirPath = savePath + documentSaveFolder;

        emptyFolder(imageSaveDirPath);

        //Compile images from the document to display on the website
        // Loop through all the pages of PDF file
        for (int pageCount = 1; pageCount <= pdfDocument.getPages().size(); pageCount++) {
            // Create stream object to save the output image

            try (OutputStream imageStream = new FileOutputStream(imageSaveDirPath + "outputImage" + pageCount + ".png")){
                // Create Resolution object
                Resolution resolution = new Resolution(300);
                // Create PngDevice object with particular resolution
                PngDevice pngDevice = new PngDevice(resolution);
                // Convert a particular page and save the image to stream
                pngDevice.process(pdfDocument.getPages().get_Item(pageCount), imageStream);
            }
            catch (Exception e) {
                System.out.println("Error happened when generating pdf images -> stack: " + e.getStackTrace());
            }
        }

        System.out.println("Finished generating images from pdf!");
    }

    private void emptyFolder(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                f.delete();
            }
        }
    }

}
