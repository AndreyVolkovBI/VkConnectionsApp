package com.andreyvolkov.vkconnectionsapp.UI.Helper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch.VerifiedSearchMainFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectItemRecyclerViewAdapter extends RecyclerView.Adapter<SelectItemRecyclerViewAdapter.SelectItemViewHolder>{

    // all the types
    private final int TYPE_ITEM_CATEGORY = 0;
    private final int TYPE_ITEM_PERSON = 1;
    private final int TYPE_ITEM_BLUE_LINE = 2;
    private final int TYPE_ITEM_GRAY_LINE = 3;

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private Context context;

    public SelectItemRecyclerViewAdapter(Context context, ArrayList<String> personResultImages, ArrayList<String> personResultNames, ArrayList<String> personResultIds) {
        this.images = personResultImages;
        this.names = personResultNames;
        this.ids = personResultIds;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        SelectItemViewHolder holder;

        switch (viewType) {
            case TYPE_ITEM_CATEGORY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item, parent, false);
                holder = new SelectItemViewHolder(view);
                break;
            case TYPE_ITEM_PERSON:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_person_item, parent, false);
                holder = new SelectItemViewHolder(view);
                break;
            case TYPE_ITEM_GRAY_LINE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_gray_line, parent, false);
                holder = new SelectItemViewHolder(view);
                break;
            case TYPE_ITEM_BLUE_LINE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_blue_line, parent, false);
                holder = new SelectItemViewHolder(view);
                break;
            default:
                holder = null;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectItemViewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case TYPE_ITEM_PERSON:
                Glide.with(context)
                        .asBitmap()
                        .load(images.get(position))
                        .into(holder.itemImage);

                holder.itemName.setText(names.get(position));
                holder.itemId.setText(ids.get(position));
                holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, names.get(position), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case TYPE_ITEM_CATEGORY:
                Glide.with(context)
                        .asBitmap()
                        .load(images.get(position))
                        .into(holder.itemImage);

                holder.itemName.setText(names.get(position));
                holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment selectedFragment = new VerifiedSearchMainFragment(
                                names.get(position), images.get(position), null, null, null);
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.verified_search_container, selectedFragment).commit();
                    }
                });
                break;
            case TYPE_ITEM_GRAY_LINE:
                break;
            case TYPE_ITEM_BLUE_LINE:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (names.get(position) == "2") { return TYPE_ITEM_GRAY_LINE; }
        else if (names.get(position) == "1") { return TYPE_ITEM_BLUE_LINE; }
        else if (ids.size() == 0) {return TYPE_ITEM_CATEGORY; }
        else { return TYPE_ITEM_PERSON; }
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class SelectItemViewHolder extends RecyclerView.ViewHolder {

        CircleImageView itemImage;
        TextView itemName;
        TextView itemId;
        RelativeLayout itemLayout;
        View blueLineView;
        View grayLineView;

        public SelectItemViewHolder(View itemView) {
            super(itemView);

            // 3 lists work for both categories and famous people
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemId = itemView.findViewById(R.id.itemId);

            itemLayout = itemView.findViewById(R.id.itemLayout);

            // all the lines
            blueLineView = itemView.findViewById(R.id.resultBlueLineView);
            grayLineView = itemView.findViewById(R.id.resultGrayLineView);
        }
    }
}
