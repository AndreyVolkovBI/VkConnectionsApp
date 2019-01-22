package com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andreyvolkov.vkconnectionsapp.Model.JSONDataClient;
import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Helper.ResultRecyclerViewAdapter;
import com.andreyvolkov.vkconnectionsapp.UI.Helper.SelectItemRecyclerViewAdapter;

import java.util.ArrayList;

public class SelectItemFragment extends Fragment {

    public SelectItemFragment() {}

    private String itemName; // category || person
    private String selectedCategoryName;
    private String selectedCategoryImage;

    private TextView selectItemFragmentCapName;
    private RecyclerView recyclerView;

    // all the arrays
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();

    JSONDataClient json = new JSONDataClient();

    @SuppressLint("ValidFragment")
    public SelectItemFragment(String itemName, String selectedCategoryName, String selectedCategoryImage) {
        this.itemName = itemName;
        this.selectedCategoryName = selectedCategoryName;
        this.selectedCategoryImage = selectedCategoryImage;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verified_select_item_fragment, container, false);
        init(view);
        setContent();

        return view;
    }

    private void init(View view) {
        selectItemFragmentCapName = view.findViewById(R.id.selectItemFragmentCapName);
        recyclerView = view.findViewById(R.id.selectItemFragmentRecyclerView);
    }

    private void setContent() {

        if (itemName == "category") {
            selectItemFragmentCapName.setText("Выберите категорию");
            initRecyclerViewForCategories();
        } else if (itemName == "person") {
            selectItemFragmentCapName.setText("Выберите пользователя");
            initRecyclerViewForPeople();
        }
    }

    private void initRecyclerViewForCategories() {
        images = json.getCategoriesUrls();
        names = json.getCategoriesNames();

        SelectItemRecyclerViewAdapter adapter = new SelectItemRecyclerViewAdapter(
                getContext(), images, names, ids);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void initRecyclerViewForPeople() {
        json.getVerifiedUsersByCategoryName(selectedCategoryName);

        images = json.getVerifiedPeopleImages();
        names = json.getVerifiedPeopleNames();
        ids = json.getVerifiedPeopleIds();

        ResultRecyclerViewAdapter adapter = new ResultRecyclerViewAdapter(
                getContext(), images, names, ids, selectedCategoryName, selectedCategoryImage);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
