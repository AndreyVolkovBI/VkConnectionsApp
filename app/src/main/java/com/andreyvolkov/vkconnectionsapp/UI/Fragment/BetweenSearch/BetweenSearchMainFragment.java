package com.andreyvolkov.vkconnectionsapp.UI.Fragment.BetweenSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.SearchResultFragment;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class BetweenSearchMainFragment extends Fragment {

    EditText betweenSearchUserFromLink;
    EditText betweenSearchUserToLink;
    Button betweenSearchButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.between_search_main_layout, container, false);
        init(view);

        betweenSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userFrom = betweenSearchUserFromLink.getText().toString();
                String userTo = betweenSearchUserToLink.getText().toString();
                if (checkStrings(userFrom, userTo)) {
                    Fragment selectedFragment = new SearchResultFragment(userFrom, userTo, "between");
                    getFragmentManager().beginTransaction().replace(R.id.between_search_container, selectedFragment).commit();
                } else {
                    Toast.makeText(getContext(), "Заполните оба поля", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void init(View view) {
        betweenSearchUserFromLink = view.findViewById(R.id.betweenLinkFrom);
        betweenSearchUserToLink = view.findViewById(R.id.betweenLinkTo);
        betweenSearchButton = view.findViewById(R.id.betweenSearchButton);
    }

    private boolean checkStrings(String firstString, String secondString) {
        if (!TextUtils.isEmpty(firstString) && !TextUtils.isEmpty(secondString))
            return true;
        return false;
    }
}
