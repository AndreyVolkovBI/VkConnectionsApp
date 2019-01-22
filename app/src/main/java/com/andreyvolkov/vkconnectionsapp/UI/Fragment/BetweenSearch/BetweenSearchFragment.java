package com.andreyvolkov.vkconnectionsapp.UI.Fragment.BetweenSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.SimpleSearch.SimpleSearchMainFragment;

public class BetweenSearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.between_search_fragment, container, false);

        Fragment selectedFragment = new BetweenSearchMainFragment();
        getFragmentManager().beginTransaction().replace(R.id.between_search_container, selectedFragment).commit();

        return view;
    }
}
