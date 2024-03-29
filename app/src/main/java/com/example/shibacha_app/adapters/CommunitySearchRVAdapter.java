package com.example.shibacha_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shibacha_app.R;
import com.example.shibacha_app.models.CommunityModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommunitySearchRVAdapter extends RecyclerView.Adapter<CommunitySearchRVAdapter.ViewHolder> {
    private ArrayList<CommunityModel> communityList;
    private Context context;
    int last = -1;
    private CommunityClickInterface communityClickInterface;

    public CommunitySearchRVAdapter(ArrayList<CommunityModel> communityList, Context context, CommunityClickInterface communityClickInterface) {
        this.communityList = communityList;
        this.context = context;
        this.communityClickInterface = communityClickInterface;
    }

    // Set the views for each item
    @NonNull
    @Override
    public CommunitySearchRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_search_rv_item,parent,false);
        return new ViewHolder(view);
    }

    // item View properties
    @Override
    public void onBindViewHolder(@NonNull CommunitySearchRVAdapter.ViewHolder holder, int position) {
        CommunityModel communityItem = communityList.get(position);
        holder.communityName.setText(communityItem.getCommunityName());
        holder.communityMember.setText(communityItem.getCommunityMembers().toString());
        holder.communityCategory.setText(communityItem.getCommunityCategory());
        Picasso.get().load(communityItem.getCommunityImg()).into(holder.communityDP);
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communityClickInterface.onCommunityClick(position);
            }
        });
    }

    // Animation for when view is clicked
    private void setAnimation(View itemView, int position) {
        if(position > last) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            last = position;
        }
    }

    // Return the number of communities
    @Override
    public int getItemCount() {
        return communityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView communityName;
        private ImageView communityDP;
        private TextView communityMember;
        private TextView communityCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            communityName = itemView.findViewById(R.id.community_name);
            communityDP = itemView.findViewById(R.id.community_dp);
            communityMember = itemView.findViewById(R.id.community_member);
            communityCategory = itemView.findViewById(R.id.community_category);
        }
    }

    public interface CommunityClickInterface {
        void onCommunityClick(int position);
    }

    //Filter the list shown
    public void filterList(ArrayList<CommunityModel> filteredList) {
        communityList = filteredList;
        this.notifyDataSetChanged();
    }
}
