package com.example.hp.featuredsongs.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.example.hp.featuredsongs.R;

import java.util.ArrayList;
import java.util.List;

public class SearchedSongList2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchedSongAdapter2 adapter;
    private List<SearchedSong> albumList;
    private  String searchedString ;

    public SearchedSongList2(RecyclerView recyclerView, SearchedSongAdapter2 adapter, List<SearchedSong> albumList) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.albumList = albumList;
    }
    public SearchedSongList2() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_song_list);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Search Results");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            getSupportActionBar().setTitle("Search Results");
        }
        Intent i = getIntent();
        searchedString = i.getStringExtra("searchedString");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        albumList = new ArrayList<>();
        adapter = new SearchedSongAdapter2(this, albumList);
        prepareAlbums();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album11,
               };

        SearchedSong a = new SearchedSong(searchedString, 3.01 , covers[0]);
        albumList.add(a);



        adapter.notifyDataSetChanged();
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
