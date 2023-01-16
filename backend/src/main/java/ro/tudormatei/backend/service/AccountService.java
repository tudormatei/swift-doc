package ro.tudormatei.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AccountService {
    public boolean createAccount(String email, String pass) {
        Path currentRelativePath = Paths.get("");
        String jsonPath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\data\\";
        jsonPath = jsonPath + documentSaveFolder + "accounts.json";

        File jsonFile = new File(jsonPath);

        if (jsonFile.exists()) {
            // Create a new ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            try {
                // Read the JSON data from the file
                JsonNode json = mapper.readTree(new File(jsonPath));

                System.out.println(json);
                if (((ObjectNode) json).has(email)) {
                    return false;
                }

                // Make changes to the JSON data
                //Maybe some kind of password check
                ((ObjectNode) json).put(email, pass);

                // Write the modified JSON data back to the file
                mapper.writeValue(new File(jsonPath), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Create a new ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Create a new JSON object
            ObjectNode json = mapper.createObjectNode();
            json.put(email, pass);
            try {
                // Write the JSON object to a file
                mapper.writeValue(new File(jsonPath), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public boolean loginAccount(String email, String password) {
        Path currentRelativePath = Paths.get("");
        String jsonPath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\data\\";
        jsonPath = jsonPath + documentSaveFolder + "accounts.json";

        File jsonFile = new File(jsonPath);

        if (!jsonFile.exists()) {
            return false;
        }

        // Create a new ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Read the JSON data from the file
            JsonNode json = mapper.readTree(new File(jsonPath));

            if(((ObjectNode) json).get(email) == null) {
                return false;
            }

            if (((ObjectNode) json).get(email).asText().equals(password)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteAccountDatabase() {
        Path currentRelativePath = Paths.get("");
        String jsonPath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\data\\";
        jsonPath = jsonPath + documentSaveFolder + "accounts.json";

        File jsonFile = new File(jsonPath);

        if (!jsonFile.exists()) {
            return false;
        }

        jsonFile.delete();
        return true;
    }
}
