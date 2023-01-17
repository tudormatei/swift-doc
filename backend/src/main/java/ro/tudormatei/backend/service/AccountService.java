package ro.tudormatei.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class AccountService {
    public boolean createAccount(String email, String pass) {
        System.out.println("Creating new account, email: " + email + ", password: " + pass);
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

                if (((ObjectNode) json).has(email)) {
                    System.out.println("Email is already in use, email: " + email);
                    return false;
                }

                // Make changes to the JSON data
                //Maybe some kind of password check
                ((ObjectNode) json).put(email, pass);
                createDefaultData(email);

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
            createDefaultData(email);
            try {
                // Write the JSON object to a file
                mapper.writeValue(new File(jsonPath), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Creation of account is successful!");
        return true;
    }

    public boolean loginAccount(String email, String password) {
        System.out.println("Attempting to log in user, email: " + email + ", password " + password);
        Path currentRelativePath = Paths.get("");
        String jsonPath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\data\\";
        jsonPath = jsonPath + documentSaveFolder + "accounts.json";

        File jsonFile = new File(jsonPath);

        if (!jsonFile.exists()) {
            System.out.println("Account database doesn't exist");
            return false;
        }

        // Create a new ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Read the JSON data from the file
            JsonNode json = mapper.readTree(new File(jsonPath));

            if (((ObjectNode) json).get(email).asText() == null) {
                return false;
            }

            if (((ObjectNode) json).get(email).asText().equals("")) {
                return false;
            }

            if (((ObjectNode) json).get(email).asText().equals(password)) {
                System.out.println("Login was successful!");
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

    private void createDefaultData(String email) {
        Path currentRelativePath = Paths.get("");
        String jsonPath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\data\\";
        jsonPath = jsonPath + documentSaveFolder + "userData.json";

        File jsonFile = new File(jsonPath);

        if (jsonFile.exists()) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                // Read the JSON data from the file
                JsonNode json = mapper.readTree(new File(jsonPath));

                ObjectNode data = mapper.createObjectNode();
                data.put("empty", "empty");

                ((ObjectNode) json).replace(email, data);
                mapper.writeValue(new File(jsonPath), json);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Create a new ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Create a new JSON object
            ObjectNode json = mapper.createObjectNode();
            ObjectNode data = mapper.createObjectNode();
            data.put("empty", "empty");

            json.put(email, data);

            try {
                mapper.writeValue(new File(jsonPath), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateUserData(Map<String, String> userData) {
        String email = userData.remove("email");

        System.out.println("Updating user data for user: " + email);

        Path currentRelativePath = Paths.get("");
        String jsonPath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\data\\";
        jsonPath = jsonPath + documentSaveFolder + "userData.json";

        File jsonFile = new File(jsonPath);

        if (jsonFile.exists()) {

            // Create a new ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            try {
                // Read the JSON data from the file
                JsonNode json = mapper.readTree(new File(jsonPath));

                if (!((ObjectNode) json).has(email)) {
                    return false;
                }

                ObjectNode data = mapper.createObjectNode();
                for (Map.Entry<String, String> entry : userData.entrySet())
                    data.put(entry.getKey(), entry.getValue());


                ((ObjectNode) json).replace(email, data);
                mapper.writeValue(new File(jsonPath), json);

                return true;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Create a new ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Create a new JSON object
            ObjectNode json = mapper.createObjectNode();
            ObjectNode data = mapper.createObjectNode();

            for (Map.Entry<String, String> entry : userData.entrySet())
                data.put(entry.getKey(), entry.getValue());

            json.put(email, data);

            try {
                mapper.writeValue(new File(jsonPath), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public Map<String, String> getUserData(String email) {
        System.out.println("Getting the user data for user: " + email);
        Path currentRelativePath = Paths.get("");
        String jsonPath = currentRelativePath.toAbsolutePath().toString();

        String documentSaveFolder = "\\data\\";
        jsonPath = jsonPath + documentSaveFolder + "userData.json";

        File jsonFile = new File(jsonPath);

        if (!jsonFile.exists()) {
            return null;
        }

        // Create a new ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> userData = new HashMap<>();

        try {
            JsonNode json = mapper.readTree(new File(jsonPath));

            for (Iterator<Map.Entry<String, JsonNode>> it = json.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> node = it.next();
                if(node.getKey().equals(email)){
                    for (Iterator<Map.Entry<String, JsonNode>> iter = node.getValue().fields(); iter.hasNext(); ) {
                        Map.Entry<String, JsonNode> data = iter.next();
                        userData.put(data.getKey(), data.getValue().asText());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userData;
    }
}
