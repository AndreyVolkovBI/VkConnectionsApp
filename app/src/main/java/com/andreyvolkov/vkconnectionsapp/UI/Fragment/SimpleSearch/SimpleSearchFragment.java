package com.andreyvolkov.vkconnectionsapp.UI.Fragment.SimpleSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andreyvolkov.vkconnectionsapp.R;

public class SimpleSearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_search_fragment, container, false);

        Fragment selectedFragment = new SimpleSearchMainFragment();
        getFragmentManager().beginTransaction().replace(R.id.simple_search_container, selectedFragment).commit();

        return view;
    }
}
