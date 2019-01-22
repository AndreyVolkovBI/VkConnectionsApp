package com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch.ReflectItem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andreyvolkov.vkconnectionsapp.R;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerifiedItem extends Fragment {

    // category views
    private CircleImageView layoutCategoryItemImage;
    private TextView layoutCategoryItemCategoryName;

    // person views
    private CircleImageView layoutPersonItemImage;
    private TextView layoutPersonItemName;
    private TextView layoutPersonItemId;

    private boolean isCategory;
    // category var
    private String categoryName;
    private String categoryImage;
    // person var
    private String personName;
    private String personId;
    private String personImage;

    public VerifiedItem() {}

    @SuppressLint("ValidFragment")
    public VerifiedItem(boolean isCategory, String categoryName, String categoryImage, String personName, String personId, String personImage) {
        this.isCategory = isCategory;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.personName = personName;
        this.personId = personId;
        this.personImage = personImage;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        if (isCategory) {
            view = inflater.inflate(R.layout.verified_layout_category_item, container, false);
            initCategory(view);
        } else {
            view = inflater.inflate(R.layout.verified_layout_person_item, container, false);
            initPerson(view);
        }
        return view;
    }

    private void initCategory(View view) {
        layoutCategoryItemImage = view.findViewById(R.id.layoutCategoryItemImage);
        layoutCategoryItemCategoryName = view.findViewById(R.id.layoutCategoryItemCategoryName);

        Glide.with(getContext())
                .asBitmap()
                .load(categoryImage)
                .into(layoutCategoryItemImage);
        layoutCategoryItemCategoryName.setText(categoryName);

    }

    private void initPerson(View view) {
        layoutPersonItemImage = view.findViewById(R.id.itemImage);
        layoutPersonItemName = view.findViewById(R.id.itemName);
        layoutPersonItemId = view.findViewById(R.id.itemId);

        Glide.with(getContext())
                .asBitmap()
                .load(personImage)
                .into(layoutPersonItemImage);
        layoutPersonItemName.setText(personName);
        layoutPersonItemId.setText(personId);
    }

}
