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
        wordsToSearch.put("codul numeric personal", "CNP");
        wordsToSearch.put("Subsemnatul", "fullname");
        wordsToSearch.put("str\\.", "street");
        wordsToSearch.put("nr\\.", "nr");
        wordsToSearch.put("telefon", "phonenr");
        wordsToSearch.put("bl\\.", "block");
        wordsToSearch.put("sc\\.", "stair");
        wordsToSearch.put("et\\.", "level");
        wordsToSearch.put("ap\\.", "apartment");
        wordsToSearch.put("cart\\.", "neighborhood");
        wordsToSearch.put("oras", "city");
        wordsToSearch.put("judeţul", "county");
    }

    private void SetupWordsToReplace() {
        wordsToReplace.put("date", "01/01/2023");
        wordsToReplace.put("fname", "Tudor");
        wordsToReplace.put("lname", "Matei");
        wordsToReplace.put("fullname", "Tudor Matei");
        wordsToReplace.put("street", "Castanilor");
        wordsToReplace.put("nr", "35");
        wordsToReplace.put("email", "tudormatei010@gmail.com");
        wordsToReplace.put("phonenr", "+40754225563");
        wordsToReplace.put("block", "-");
        wordsToReplace.put("stair", "-");
        wordsToReplace.put("neighborhood", "-");
        wordsToReplace.put("city", "Chitila");
        wordsToReplace.put("county", "Ilfov");
        wordsToReplace.put("CNP", "505050134425");
        wordsToReplace.put("level", "-");
        wordsToReplace.put("apartment", "-");
    }

    public HashMap<String, String> getWordsToSearch() {
        return wordsToSearch;
    }

    public HashMap<String, String> getWordsToReplace() {
        return wordsToReplace;
    }
}
