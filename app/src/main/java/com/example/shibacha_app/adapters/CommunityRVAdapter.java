package com.example.shibacha_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shibacha_app.R;
import com.example.shibacha_app.activities.EditCommunityActivity;
import com.example.shibacha_app.models.CommunityMemberModel;
import com.example.shibacha_app.models.CommunityModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommunityRVAdapter extends RecyclerView.Adapter<CommunityRVAdapter.ViewHolder> {
    private ArrayList<CommunityModel> communityList;
    private Context context;
    int last = -1;
    private CommunityClickInterface communityClickInterface;
    private String userId;
    private FirebaseAuth fAuth;
    private FirebaseFirestore db;
    private FirebaseUser currUser;
    private String uid;
    private CommunityModel comm;

    public CommunityRVAdapter(ArrayList<CommunityModel> communityList, Context context, CommunityClickInterface communityClickInterface) {
        this.communityList = communityList;
        this.context = context;
        this.communityClickInterface = communityClickInterface;
    }

    // Set the views for each item
    @NonNull
    @Override
    public CommunityRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_rv_item,parent,false);
        return new ViewHolder(view);
    }

    // item View properties
    @Override
    public void onBindViewHolder(@NonNull CommunityRVAdapter.ViewHolder holder, int position) {
        // Set values for view
        CommunityModel communityItem = communityList.get(position);
        holder.communityName.setText(communityItem.getCommunityName());
        holder.communityCategory.setText(communityItem.getCommunityCategory());
        Picasso.get().load(communityItem.getCommunityImg()).into(holder.communityDP);
        setAnimation(holder.itemView, position);

        // Initialize user values
        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        currUser = fAuth.getCurrentUser();
        uid = currUser.getUid();

        // Check user role
        Query query = db.collection("CommunityMembers").whereEqualTo("communityId", communityItem.getCommunityId()).whereEqualTo("userId", uid);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String role = document.get("role").toString();
                        Log.d("CommTest", role);
                        if (role.equals("Member")) {
                            Log.d("Test", "Is a member");
                            holder.editBtn.setVisibility(View.GONE);
                            holder.leaveBtn.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Log.d("Test", "Error getting documents", task.getException());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communityClickInterface.onCommunityClick(position);
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EditCommunityActivity.class);
                i.putExtra("community", communityItem);
                context.startActivity(i);
            }
        });

        holder.leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TestLeave", "Pressed");
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TestLeave", document.getId());
                                db.collection("CommunityMembers").document(document.getId()).delete();
                            }
                        } else {
                            Log.d("Test", "Error getting documents", task.getException());
                        }
                    }
                });
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
        private Button editBtn;
        private Button leaveBtn;
        private TextView communityCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            communityName = itemView.findViewById(R.id.community_name);
            communityDP = itemView.findViewById(R.id.community_dp);
            editBtn = itemView.findViewById(R.id.edit_button);
            leaveBtn = itemView.findViewById(R.id.leave_button);
            communityCategory = itemView.findViewById(R.id.community_category);
        }
    }

    public interface CommunityClickInterface {
        void onCommunityClick(int position);
    }
}
