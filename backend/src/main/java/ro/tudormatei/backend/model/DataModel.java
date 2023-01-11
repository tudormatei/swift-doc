package ro.tudormatei.backend.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DataModel {

    private ArrayList<String> wordsToSearch;
    private HashMap<String, String> wordsToReplace;

    public DataModel() {
        wordsToSearch = new ArrayList<>();
        SetupWordsToSearch();
        wordsToReplace = new HashMap<>();
        SetupWordsToReplace();
    }

    private void SetupWordsToSearch() {
        wordsToSearch.add("Data");
        wordsToSearch.add("Subsemnatul");
        wordsToSearch.add("nr.");
        wordsToSearch.add("str.");
    }

    private void SetupWordsToReplace() {
        wordsToReplace.put("data", "20:10:2023");
        wordsToReplace.put("subsemnatul", "Tudor Matei");
        wordsToReplace.put("nr\\.", "35");
        wordsToReplace.put("str\\.", "strada castanilor");
    }

    public ArrayList<String> getWordsToSearch() {
        return wordsToSearch;
    }

    public HashMap<String, String> getWordsToReplace() {
        return wordsToReplace;
    }
}
