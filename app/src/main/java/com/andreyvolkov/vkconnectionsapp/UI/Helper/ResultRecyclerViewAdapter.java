package com.andreyvolkov.vkconnectionsapp.UI.Helper;

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

import com.andreyvolkov.vkconnectionsapp.Model.JSONDataClient;
import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch.VerifiedSearchMainFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "ResultRecyclerViewAdapter";

    JSONDataClient json = new JSONDataClient();

    // all the types
    private final int TYPE_ITEM_PERSON = 0;
    private final int TYPE_ITEM_BLUE_LINE = 1;
    private final int TYPE_ITEM_GRAY_LINE = 2;

    private ArrayList<String> personResultImages = new ArrayList<>();
    private ArrayList<String> personResultNames = new ArrayList<>();
    private ArrayList<String> personResultIds = new ArrayList<>();
    private Context context;

    private String selectedCategoryName;
    private String selectedCategoryImage;

    public ResultRecyclerViewAdapter(Context context, ArrayList<String> personResultImages,
                                     ArrayList<String> personResultNames, ArrayList<String> personResultIds,
                                     String selectedCategoryName, String selectedCategoryImage) {
        this.personResultImages = personResultImages;
        this.personResultNames = personResultNames;
        this.personResultIds = personResultIds;

        this.selectedCategoryName = selectedCategoryName;
        this.selectedCategoryImage = selectedCategoryImage;

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        ViewHolder holder;

        switch (viewType) {
            case TYPE_ITEM_PERSON:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_person_item, parent, false);
                holder = new ViewHolder(view);
                break;
            case TYPE_ITEM_GRAY_LINE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_gray_line, parent, false);
                holder = new ViewHolder(view);
                break;
            case TYPE_ITEM_BLUE_LINE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_blue_line, parent, false);
                holder = new ViewHolder(view);
                break;
            default:
                holder = null;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case TYPE_ITEM_PERSON:
                Glide.with(context)
                        .asBitmap()
                        .load(personResultImages.get(position))
                        .into(holder.itemImage);

                holder.itemName.setText(personResultNames.get(position));
                final String itemId;
                if (personResultIds.get(position) instanceof String){
                    itemId = personResultIds.get(position);
                    holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, personResultNames.get(position), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    itemId = "id" + String.valueOf(personResultIds.get(position));
                    holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Fragment selectedFragment = new VerifiedSearchMainFragment(
                                    selectedCategoryName, selectedCategoryImage,
                                    personResultNames.get(position), itemId, personResultImages.get(position));
                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.verified_search_container, selectedFragment).commit();
                        }
                    });
                }
                holder.itemId.setText(itemId);
                break;
            case TYPE_ITEM_GRAY_LINE:
                break;
            case TYPE_ITEM_BLUE_LINE:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (personResultNames.get(position) == "2") { return TYPE_ITEM_GRAY_LINE; }
        else if (personResultNames.get(position) == "1") { return TYPE_ITEM_BLUE_LINE; }
        else { return TYPE_ITEM_PERSON; }
    }

    @Override
    public int getItemCount() {
        return personResultNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView itemImage;
        TextView itemName;
        TextView itemId;
        RelativeLayout itemLayout;
        View blueLineView;
        View grayLineView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemId = itemView.findViewById(R.id.itemId);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            blueLineView = itemView.findViewById(R.id.resultBlueLineView);
            grayLineView = itemView.findViewById(R.id.resultGrayLineView);
        }
    }
}
