package com.andreyvolkov.vkconnectionsapp.UI.Fragment.SimpleSearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Activity.WelcomeActivity;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.SearchResultFragment;
import com.bumptech.glide.Glide;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SimpleSearchMainFragment extends Fragment {

    Map<String, String> data = new HashMap<>();

    // layout_my_profile
    private CircleImageView personImage;
    private TextView personName;
    private TextView personId;
    private TextView exitButton;

    EditText simpleSearchLink;
    Button simpleSearchButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_serach_main_layout, container, false);
        init(view);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("userId", Context.MODE_PRIVATE);
                sp.edit().clear().commit();
                Intent intent = new Intent(getContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

        simpleSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences("userId", Context.MODE_PRIVATE);
                String ownerId = sharedPref.getString("id", "");
                Fragment selectedFragment = new SearchResultFragment(
                        ownerId, simpleSearchLink.getText().toString(), "simple");
                getFragmentManager().beginTransaction().replace(R.id.simple_search_container, selectedFragment).commit();
            }
        });

        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "id, full_name, photo_100"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKList vkList = (VKList) response.parsedModel;

                try {
                    data.put("Id", (String) vkList.get(0).fields.getString("id"));
                    data.put("FullName", (String) vkList.get(0).fields.getString("first_name")
                            + " " + (String) vkList.get(0).fields.getString("last_name"));
                    data.put("PhotoUrl", (String) vkList.get(0).fields.getString("photo_100"));

                    Glide.with(getContext())
                            .asBitmap()
                            .load(data.get("PhotoUrl"))
                            .into(personImage);

                    personName.setText(data.get("FullName"));
                    personId.setText("id" + data.get("Id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void init(View view) {
        personImage = view.findViewById(R.id.personImage);
        personName = view.findViewById(R.id.personName);
        personId = view.findViewById(R.id.personId);
        exitButton = view.findViewById(R.id.exitButton);

        simpleSearchLink = view.findViewById(R.id.simpleSearchLink);
        simpleSearchButton = view.findViewById(R.id.simpleSearchButton);
    }
}
