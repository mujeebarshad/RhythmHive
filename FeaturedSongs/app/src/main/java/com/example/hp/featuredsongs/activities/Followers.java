package com.example.hp.featuredsongs.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.featuredsongs.R;

import java.util.ArrayList;
import java.util.List;

public class Followers extends Fragment {
    private RecyclerView recyclerView;
    private UserFollowersAdapter adapter;
    private List<UserFollowers> albumList;
    ;


    public Followers() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_followers, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.user_followers);
        albumList = new ArrayList<>();
        adapter = new UserFollowersAdapter(getContext(), albumList);
        prepareAlbums();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return myView;
    }

    private void prepareAlbums() {

        int[] covers = new int[]{
                R.drawable.album11,
                R.drawable.album7,
                R.drawable.album4,


        };

        UserFollowers a1 = new UserFollowers("User 1", covers[0]);
        albumList.add(a1);
        UserFollowers a2 = new UserFollowers("User 2", covers[1]);
        albumList.add(a2);
        UserFollowers a3 = new UserFollowers("User 3", covers[2]);
        albumList.add(a3);
        adapter.notifyDataSetChanged();
    }

}
