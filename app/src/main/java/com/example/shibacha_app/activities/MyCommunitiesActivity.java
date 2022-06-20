package com.example.shibacha_app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shibacha_app.R;
import com.example.shibacha_app.adapters.CommunityRVAdapter;
import com.example.shibacha_app.models.CommunityModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyCommunitiesActivity extends AppCompatActivity implements CommunityRVAdapter.CommunityClickInterface{

    private RecyclerView communityRV;
    private FloatingActionButton addCommunity;
    private FirebaseDatabase fireDB;
    private DatabaseReference dbRef;
    private ArrayList<CommunityModel> communityList;
    private CommunityRVAdapter communityRVAdapter;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_communities);

        // initialize values
        communityRV = findViewById(R.id.recycler_view);
        addCommunity = findViewById(R.id.add_community_button);
        fireDB = FirebaseDatabase.getInstance();
        dbRef = fireDB.getReference("Communities");
        communityList = new ArrayList<>();
        communityRVAdapter = new CommunityRVAdapter(communityList, this, this);

        // set how to display recycler view
        communityRV.setLayoutManager(new GridLayoutManager(this, 2));
        //set adapter for recycler view
        communityRV.setAdapter(communityRVAdapter);

        // logic for when add button is clicked
        addCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCommunitiesActivity.this, CreateCommunityActivity.class));
            }
        });
        
        getAllCommunities();
    }

    // Show all the communities
    private void getAllCommunities() {
        communityList.clear();
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // add the value from the model
                communityList.add(snapshot.getValue(CommunityModel.class));
                // notify new addition
                communityRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                communityRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                communityRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                communityRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void editCommunities() {
        //TODO: Edit button leads to edit page
    }

    // When the community is clicked on
    @Override
    public void onCommunityClick(int position) {
        //TODO: Redirect to chat page
        Intent i = new Intent(MyCommunitiesActivity.this, EditCommunityActivity.class);
        i.putExtra("community", communityList.get(position));
        startActivity(i);
    }
}