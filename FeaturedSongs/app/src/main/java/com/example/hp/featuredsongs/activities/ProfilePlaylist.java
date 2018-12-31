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


public class ProfilePlaylist extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView recyclerView;
    private ProfilePlaylistAdapter adapter;
    private List<PlaylistSong> albumList;
    ;


    public ProfilePlaylist() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_profile_playlist, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.profile_playlist);
        albumList = new ArrayList<>();
        adapter = new ProfilePlaylistAdapter(getContext(), albumList);
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
