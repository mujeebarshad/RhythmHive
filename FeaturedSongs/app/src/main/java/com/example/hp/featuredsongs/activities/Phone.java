package com.example.hp.featuredsongs.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hp.featuredsongs.R;

import java.util.ArrayList;
import java.util.List;

public class Phone extends Fragment  {

    private RecyclerView recyclerView;
    private PhoneAdapter adapter;
    private List<PlaylistSong> albumList;
    private SearchView searchView;
    private Button mButton;

    @SuppressLint("ValidFragment")
    public Phone(RecyclerView recyclerView, PhoneAdapter adapter, List<PlaylistSong> albumList) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.albumList = albumList;
    }

    public Phone() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.phone, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view2);
        albumList = new ArrayList<>();
        adapter = new PhoneAdapter(getContext(), albumList);
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

        PlaylistSong a1 = new PlaylistSong("Title 1", 4.22 , covers[0]);
        albumList.add(a1);
        PlaylistSong a2 = new PlaylistSong("Title 2", 2.43 , covers[1]);
        albumList.add(a2);
        PlaylistSong a3 = new PlaylistSong("Title 3", 3.13 , covers[2]);
        albumList.add(a3);
        adapter.notifyDataSetChanged();
    }


}