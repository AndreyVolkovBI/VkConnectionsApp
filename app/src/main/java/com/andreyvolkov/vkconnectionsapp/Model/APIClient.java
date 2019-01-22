package com.andreyvolkov.vkconnectionsapp.Model;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIClient {

    JSONParseHelper parse = new JSONParseHelper();

    private String baseURL = "http://vk-connections-api.herokuapp.com/?";
    String sep = "&";

    private ArrayList<String> personResultImages = new ArrayList<>();
    private ArrayList<String> personResultNames = new ArrayList<>();
    private ArrayList<String> personResultIds = new ArrayList<>();

    public ArrayList<String> getPersonResultImages() {
        return personResultImages;
    }

    public ArrayList<String> getPersonResultNames() {
        return personResultNames;
    }

    public ArrayList<String> getPersonResultIds() {
        return personResultIds;
    }

    public void postUserToDb(String vkId, String fullName) {

        String deviceModel = android.os.Build.MODEL;
        String androidVersion = android.os.Build.VERSION.RELEASE;

        final String postUserQuery = baseURL + "method=post" + sep + "id=" + vkId + sep + "full_name=" + fullName + sep + "device_model=" + deviceModel + sep + "android_version=" + androidVersion;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(postUserQuery)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String trueResponse = response.body().string();
            }
        });

    }

    public void makeRequest(String method, String owner, String fromId, String toId, String friends) {
        String query = baseURL + "method=" + method + sep + "owner=" + owner + sep + "from=" + fromId + sep + "to=" + toId;
        if (friends != null) {
            query += sep + "friends=" + friends;
        }
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(query)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bodyResponse = response.body().string();
                ArrayList<ArrayList<HashMap<String, String>>> result = getResultArray(bodyResponse);
                if (result != null){
                    fillLists(result);
                } else {
                    makeListsNull();
                }
            }
        });

    }

    private ArrayList<ArrayList<HashMap<String, String>>> getResultArray(String bodyResponse) {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = new ByteArrayInputStream(Charset.forName("UTF-8").encode(bodyResponse).array());
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));

            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            in.close();

            ArrayList<Object> ja = parse.toList(new JSONArray(sb.toString()));

            ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();
            for (int i = 0; i < ja.size(); i++) {
                result.add((ArrayList<HashMap<String, String>>) ja.get(i));
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void fillLists(ArrayList<ArrayList<HashMap<String, String>>> result) {
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                // adding request's data
                personResultIds.add("id" + String.valueOf(result.get(i).get(j).get("id")));
                personResultNames.add(result.get(i).get(j).get("full_name"));
                personResultImages.add(result.get(i).get(j).get("photo"));

                // adding gray line
                personResultIds.add("2");
                personResultNames.add("2");
                personResultImages.add("2");
            }
            // deleting useless elements
            personResultIds.remove(personResultIds.size() - 1);
            personResultNames.remove(personResultNames.size() - 1);
            personResultImages.remove(personResultImages.size() - 1);

            // adding blue line
            personResultIds.add("1");
            personResultNames.add("1");
            personResultImages.add("1");
        }
        personResultIds.remove(personResultIds.size() - 1);
        personResultNames.remove(personResultNames.size() - 1);
        personResultImages.remove(personResultImages.size() - 1);
    }

    private void makeListsNull() {
        personResultIds = null;
        personResultNames = null;
        personResultImages = null;
    }

}
