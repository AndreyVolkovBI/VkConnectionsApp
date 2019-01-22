package com.andreyvolkov.vkconnectionsapp.UI.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.andreyvolkov.vkconnectionsapp.Model.APIClient;
import com.andreyvolkov.vkconnectionsapp.Model.JSONParseHelper;
import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.BetweenSearch.BetweenSearchFragment;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.SimpleSearch.SimpleSearchMainFragment;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch.VerifiedSearchFragment;
import com.andreyvolkov.vkconnectionsapp.UI.Helper.ResultRecyclerViewAdapter;
import com.google.gson.JsonArray;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultFragment extends Fragment{
    public SearchResultFragment() { }

    APIClient client = new APIClient();
    JSONParseHelper parse = new JSONParseHelper();

    private RecyclerView recyclerView;

    private ArrayList<String> personResultImages = new ArrayList<>();
    private ArrayList<String> personResultNames = new ArrayList<>();
    private ArrayList<String> personResultIds = new ArrayList<>();

    // owner friends
    ArrayList<String> ownerFriends = new ArrayList<>();

    private String userFrom;
    private String userTo;
    private String previousScreen;

    private Button searchResultButton;

    @SuppressLint("ValidFragment")
    public SearchResultFragment(String userFrom, String userTo, String previousScreen) {
        this.userFrom = getPureLink(userFrom);
        this.userTo = getPureLink(userTo);
        this.previousScreen = previousScreen;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_layout, container, false);

        recyclerView = view.findViewById(R.id.resultRecyclerView);
        searchResultButton = view.findViewById(R.id.searchResultButton);
        makeRequest();
        return view;
    }

    private void makeRequest() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        final String ownerId = sharedPref.getString("id", "");
        if (previousScreen == "between"){
            client.makeRequest(previousScreen, ownerId, userFrom, userTo, null);
        } else {
            VKRequest friendsRequest = VKApi.friends().get(VKParameters.from(VKApiConst.USER_ID, ownerId));

            friendsRequest.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);

                    try {
                        JSONObject jsonObject = response.json;
                        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("items");
                        ArrayList<Object> ownerFriendsObject = parse.toList(jsonArray);

                        for (int i = 0; i < ownerFriendsObject.size(); i++) {
                            ownerFriends.add(String.valueOf(ownerFriendsObject.get(i)));
                        }
                        String stringOwnerFriends = getStringFromOwnerFriendsArray();

                        client.makeRequest(previousScreen, ownerId, userFrom, userTo, stringOwnerFriends);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        class UICheckAsyncTask extends AsyncTask<Void, Integer, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (client.getPersonResultNames() != null){
                    initRecyclerView();
                } else {
                    Toast.makeText(getContext(), "Данный пользователь не доступен", Toast.LENGTH_LONG).show();
                    goToPreviousFragment();
                }

                searchResultButton.setText("Новый поиск");
                searchResultButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToPreviousFragment();
                    }
                });
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    while (true){
                        if (client.getPersonResultNames() == null || client.getPersonResultNames().size() != 0) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        new UICheckAsyncTask().execute();
    }

    public void initRecyclerView(){
        personResultImages = client.getPersonResultImages();
        personResultNames = client.getPersonResultNames();
        personResultIds = client.getPersonResultIds();

        ResultRecyclerViewAdapter adapter = new ResultRecyclerViewAdapter(
                getContext(), personResultImages, personResultNames, personResultIds, null, null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private String getPureLink(String link) {
        link = link.trim().toLowerCase();
        try {
            if (link.contains("vk.com/")){ link = link.split("vk.com/")[1]; }
            if (link.startsWith("/")) { link = link.split("/")[1]; }
            if (link.endsWith("/")) { link = link.split("/")[0]; }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return link;
    }

    private void goToPreviousFragment() {
        Fragment selectedFragment;
        if (previousScreen == "verified") {
            selectedFragment = new VerifiedSearchFragment();
            getFragmentManager().beginTransaction().replace(R.id.verified_search_container, selectedFragment).commit();
        } else if (previousScreen == "between") {
            selectedFragment = new BetweenSearchFragment();
            getFragmentManager().beginTransaction().replace(R.id.between_search_container, selectedFragment).commit();
        } else {
            selectedFragment = new SimpleSearchMainFragment();
            getFragmentManager().beginTransaction().replace(R.id.simple_search_container, selectedFragment).commit();
        }
    }

    private String getStringFromOwnerFriendsArray() {
        String stringOwnerFriends = "[";

        for(int i = 0; i < ownerFriends.size(); i++) {
            stringOwnerFriends += ownerFriends.get(i) + ",";
        }
        stringOwnerFriends = stringOwnerFriends.substring(0, stringOwnerFriends.length() - 1);
        stringOwnerFriends += "]";
        return stringOwnerFriends;
    }

}
