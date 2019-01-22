package com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Activity.WelcomeActivity;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.SearchResultFragment;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch.ReflectItem.VerifiedItem;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch.ReflectItem.VerifiedTextItem;
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

public class VerifiedSearchMainFragment extends Fragment {

    Map<String, String> data = new HashMap<>();

    // My account logic
    private CircleImageView userImage;
    private TextView userName;
    private TextView userId;
    private TextView exitButton;

    // category var
    private String categoryName;
    private String categoryPhoto;

    // person var
    private String personName;
    private String personId;
    private String personPhoto;

    // Layouts
    private RelativeLayout verifiedSearchCategoryLayout;
    private RelativeLayout verifiedSearchPersonLayout;

    // search button
    private Button verifiedSearchButton;

    public VerifiedSearchMainFragment() {}
    @SuppressLint("ValidFragment")
    public VerifiedSearchMainFragment(String categoryName, String categoryPhoto, String personName, String personId, String personPhoto) {
        this.categoryName = categoryName;
        this.categoryPhoto = categoryPhoto;
        this.personName = personName;
        this.personId = personId;
        this.personPhoto = personPhoto;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verified_search_main_layout, container, false);
        init(view);
        fillImage();
        setListeners();
        return view;
    }

    private void fillImage() {
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
                            .into(userImage);

                    userName.setText(data.get("FullName"));
                    userId.setText("id" + data.get("Id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init(View view) {
        userImage = view.findViewById(R.id.personImage);
        userName = view.findViewById(R.id.personName);
        userId = view.findViewById(R.id.personId);
        exitButton = view.findViewById(R.id.exitButton);

        verifiedSearchCategoryLayout = view.findViewById(R.id.verifiedSearchCategoryLayout);
        verifiedSearchPersonLayout = view.findViewById(R.id.verifiedSearchPersonLayout);

        verifiedSearchButton = view.findViewById(R.id.verifiedSearchButton);

        Fragment categoryFragment;
        Fragment personFragment;

        if (categoryName == null) {
            categoryFragment = new VerifiedTextItem("Выберите категорию");
        } else {
            categoryFragment = new VerifiedItem(true, categoryName, categoryPhoto, null, null, null);
        }

        if (personName == null) {
            personFragment = new VerifiedTextItem("Выберите пользователя");
        } else {
            personFragment = new VerifiedItem(false, null, null, personName, personId, personPhoto);
        }

        getFragmentManager().beginTransaction().replace(R.id.verifiedSearchCategoryItem, categoryFragment).commit();
        getFragmentManager().beginTransaction().replace(R.id.verifiedSearchPersonItem, personFragment).commit();
    }

    private void setListeners() {
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("userId", Context.MODE_PRIVATE);
                sp.edit().clear().commit();
                Intent intent = new Intent(getContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

        verifiedSearchCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new SelectItemFragment("category", null, null);
                getFragmentManager().beginTransaction().replace(R.id.verified_search_container, selectedFragment).commit();
            }
        });

        verifiedSearchPersonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryName != null) {
                    Fragment selectedFragment = new SelectItemFragment("person", categoryName, categoryPhoto);
                    getFragmentManager().beginTransaction().replace(R.id.verified_search_container, selectedFragment).commit();
                } else {
                    Toast.makeText(getContext(), "Выберите категорию", Toast.LENGTH_LONG).show();
                }
            }
        });

        verifiedSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences("userId", Context.MODE_PRIVATE);
                String ownerId = sharedPref.getString("id", "");
                Fragment selectedFragment = new SearchResultFragment(
                        ownerId, personId, "verified");
                getFragmentManager().beginTransaction().replace(R.id.verified_search_container, selectedFragment).commit();
            }
        });
    }
}
