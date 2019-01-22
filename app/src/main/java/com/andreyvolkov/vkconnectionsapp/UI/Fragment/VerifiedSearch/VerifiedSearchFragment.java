package com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.SimpleSearch.SimpleSearchMainFragment;

public class VerifiedSearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verified_search_fragment, container, false);

        Fragment selectedFragment = new VerifiedSearchMainFragment();
        getFragmentManager().beginTransaction().replace(R.id.verified_search_container, selectedFragment).commit();

        return view;
    }
}
