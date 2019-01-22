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

public class VerifiedTextItem extends Fragment {

    private String hintText;
    private TextView verifiedSearchChosenText;
    public VerifiedTextItem() {}

    @SuppressLint("ValidFragment")
    public VerifiedTextItem(String hintText) { this.hintText = hintText; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verified_layout_text_item, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        verifiedSearchChosenText = view.findViewById(R.id.verifiedSearchChosenText);
        verifiedSearchChosenText.setHint(hintText);
    }
}
