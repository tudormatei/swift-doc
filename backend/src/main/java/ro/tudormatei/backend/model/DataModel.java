package ro.tudormatei.backend.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DataModel {

    private HashMap<String, String> wordsToSearch;
    private HashMap<String, String> wordsToReplace;

    public DataModel() {
        wordsToSearch = new HashMap<>();
        SetupWordsToSearch();
        wordsToReplace = new HashMap<>();
        SetupWordsToReplace();
    }

    private void SetupWordsToSearch() {
        wordsToSearch.put("Data", "date");
        wordsToSearch.put("Subsemnatul", "fullname");
        wordsToSearch.put("str.", "street");
        wordsToSearch.put("nr.", "nr");
    }

    private void SetupWordsToReplace() {
        wordsToReplace.put("date", "01/01/2023");
        wordsToReplace.put("fname", "Tudor");
        wordsToReplace.put("lname", "Matei");
        wordsToReplace.put("fullname", "Tudor Matei");
        wordsToReplace.put("street", "Castanilor");
        wordsToReplace.put("nr", "35");
        wordsToReplace.put("email", "tudormatei010@gmail.com");
    }

    public HashMap<String, String> getWordsToSearch() {
        return wordsToSearch;
    }

    public HashMap<String, String> getWordsToReplace() {
        return wordsToReplace;
    }
}
