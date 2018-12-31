package com.example.hp.featuredsongs.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hp.featuredsongs.R;

import java.util.ArrayList;
import java.util.List;

public class SelectPlaylist extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SelectedPlaylistAdapter adapter;
    private List<PlaylistList> albumList;
    private SearchView searchView;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_playlist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            getSupportActionBar().setTitle("Select Playlist");
        }
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view2);
        albumList = new ArrayList<>();
        prepareAlbums();
        adapter = new SelectedPlaylistAdapter(this, albumList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                String selected_playlist = albumList.get(position).getName();
                Intent i = new Intent(getApplicationContext(),MyMusic.class);
                Toast.makeText(SelectPlaylist.this, albumList.get(position).getName()+" was choosen", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
    private void prepareAlbums() {

        PlaylistList a1 = new PlaylistList("Playlist 1", "22-1-2018");
        albumList.add(a1);
        PlaylistList a2 = new PlaylistList("Playlist 5", "11-12-2018");
        albumList.add(a2);
        albumList.add(a1);
        PlaylistList a3 = new PlaylistList("Playlist 6", "11-12-2018");
        albumList.add(a3);


    }
}
