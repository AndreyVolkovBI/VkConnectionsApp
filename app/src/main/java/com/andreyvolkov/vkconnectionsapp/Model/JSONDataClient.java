package com.andreyvolkov.vkconnectionsapp.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JSONDataClient {

    JSONData jsonData = new JSONData();
    JSONParseHelper parse = new JSONParseHelper();
    // categories names and urls
    ArrayList<String> categoriesNames = new ArrayList<>();
    ArrayList<String> categoriesImages = new ArrayList<>();

    // verified users
    private ArrayList<String> verifiedPeopleImages = new ArrayList<>();
    private ArrayList<String> verifiedPeopleNames = new ArrayList<>();
    private ArrayList<String> verifiedPeopleIds = new ArrayList<>();

    HashMap<String, ArrayList<HashMap<String, String>>> verifiedUsers = new HashMap<>();

    public JSONDataClient() { fillCategoriesFromJson(); fillVerifiedUsersFromJson(); }

    // getters for categories
    public ArrayList<String> getCategoriesNames() { return categoriesNames; }
    public ArrayList<String> getCategoriesUrls() { return categoriesImages; }

    // getters for verified users
    public ArrayList<String> getVerifiedPeopleImages() { return verifiedPeopleImages; }
    public ArrayList<String> getVerifiedPeopleNames() { return verifiedPeopleNames; }
    public ArrayList<String> getVerifiedPeopleIds() { return verifiedPeopleIds; }

    private void fillCategoriesFromJson() {
        try {
            ArrayList<Object> categoriesObject = parse.toList(new JSONArray(jsonData.categories));
            ArrayList<HashMap<String, String>> categories = new ArrayList<>();

            for (int i = 0; i < categoriesObject.size(); i++) {
                categories.add((HashMap<String, String>) categoriesObject.get(i));
            }
            for (int i = 0; i < categories.size(); i++) {
                for (String key : categories.get(i).keySet()) {
                    categoriesNames.add(key);
                    categoriesNames.add("2");
                }
            }
            categoriesNames.remove(categoriesNames.size() - 1);
            for (int i = 0; i < categories.size(); i++) {
                for (String key : categories.get(i).keySet()) {
                    categoriesImages.add(categories.get(i).get(key));
                    categoriesImages.add("2");
                }
            }
            categoriesImages.remove(categoriesImages.size() - 1);
            System.out.print("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillVerifiedUsersFromJson() {
        try {
            HashMap<String, Object> categoriesObject = parse.toMap(new JSONObject(jsonData.verifiedUsers));
            for (int i = 0; i < categoriesObject.size(); i++) {
                for (String key : categoriesObject.keySet()) {
                    verifiedUsers.put(key, (ArrayList<HashMap<String, String>>) categoriesObject.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean getVerifiedUsersByCategoryName(String categoryName) {

        try {
            for (String key : verifiedUsers.keySet()) {
                if (key.equals(categoryName)) {
                    for (int i = 0; i < verifiedUsers.get(key).size(); i++) {
                        HashMap<String, String> hashMap = verifiedUsers.get(key).get(i);
                        verifiedPeopleImages.add(hashMap.get("photo"));
                        verifiedPeopleNames.add(hashMap.get("full_name"));
                        verifiedPeopleIds.add(hashMap.get("id"));

                        // adding gray line
                        verifiedPeopleImages.add("2");
                        verifiedPeopleNames.add("2");
                        verifiedPeopleIds.add("2");
                    }
                    // deleting useless elements
                    verifiedPeopleImages.remove(verifiedPeopleImages.size() - 1);
                    verifiedPeopleNames.remove(verifiedPeopleNames.size() - 1);
                    verifiedPeopleIds.remove(verifiedPeopleIds.size() - 1);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
