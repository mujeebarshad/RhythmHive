package com.example.hp.featuredsongs.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;


import com.example.hp.featuredsongs.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPlaylistSongs extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlaylistSongAdapter adapter;
    private List<PlaylistSong> albumList;

    public ViewPlaylistSongs(RecyclerView recyclerView, PlaylistSongAdapter adapter, List<PlaylistSong> albumList) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.albumList = albumList;
    }
    public ViewPlaylistSongs() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist_songs);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        Intent i = getIntent();
        String selectedPlaylist = i.getStringExtra("selectedPlaylist");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            getSupportActionBar().setTitle(selectedPlaylist);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        albumList = new ArrayList<>();
        adapter = new PlaylistSongAdapter(this, albumList);
        prepareAlbums();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album4,
                R.drawable.album6,

        };

        PlaylistSong a1 = new PlaylistSong("Title 1", 2.11 , covers[0]);
        albumList.add(a1);
        PlaylistSong a2 = new PlaylistSong("Title 2", 3.21 , covers[1]);
        albumList.add(a2);
        adapter.notifyDataSetChanged();
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
