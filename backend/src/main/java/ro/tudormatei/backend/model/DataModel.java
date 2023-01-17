package ro.tudormatei.backend.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataModel {

    private HashMap<String, String> wordsToSearch;
    private HashMap<String, String> wordsToReplace;

    private final String email;

    public DataModel(String user) {
        email = user;

        wordsToSearch = new HashMap<>();
        SetupWordsToSearch();
        wordsToReplace = new HashMap<>();
        SetupWordsToReplace();
    }

    private void SetupWordsToSearch() {
        System.out.println("Words to search for user " + email + " are loading...");

        Path currentRelativePath = Paths.get("");
        String jsonPath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\data\\";
        jsonPath = jsonPath + documentSaveFolder + "wordsToSearch.json";

        // Create a new ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode json = mapper.readTree(new File(jsonPath));

            for (Iterator<Map.Entry<String, JsonNode>> it = json.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> node = it.next();

                wordsToSearch.put(node.getKey(), node.getValue().asText());

                System.out.println("Loading key: " + node.getKey() + " with value: " + node.getValue().asText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SetupWordsToReplace() {
        System.out.println("Words to replace for user " + email + " are loading...");

        Path currentRelativePath = Paths.get("");
        String jsonPath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\data\\";
        jsonPath = jsonPath + documentSaveFolder + "userData.json";

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode json = mapper.readTree(new File(jsonPath));

            for (Iterator<Map.Entry<String, JsonNode>> it = json.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> node = it.next();
                if(node.getKey().equals(email)){
                    for (Iterator<Map.Entry<String, JsonNode>> iter = node.getValue().fields(); iter.hasNext(); ) {
                        Map.Entry<String, JsonNode> data = iter.next();
                        wordsToReplace.put(data.getKey(), data.getValue().asText());

                        System.out.println("Loading key: " + data.getKey() + " with value: " + data.getValue().asText());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getWordsToSearch() {
        return wordsToSearch;
    }

    public HashMap<String, String> getWordsToReplace() {
        return wordsToReplace;
    }
}
