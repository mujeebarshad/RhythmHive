package com.example.hp.featuredsongs.activities;

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

public class ViewQueue extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QueueAdapter adapter;
    private List<QueuedSong> albumList;

    public ViewQueue(RecyclerView recyclerView, QueueAdapter adapter, List<QueuedSong> albumList) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.albumList = albumList;
    }
    public ViewQueue() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Queue");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            getSupportActionBar().setTitle("Queue");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        albumList = new ArrayList<>();
        adapter = new QueueAdapter(this, albumList);
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
                R.drawable.album7,
                R.drawable.album11
        };

        QueuedSong a1 = new QueuedSong("Title 1", 2.11 , covers[0]);
        albumList.add(a1);
        QueuedSong a2 = new QueuedSong("Title 2", 3.01 , covers[1]);
        albumList.add(a2);
        QueuedSong a3 = new QueuedSong("Title 3", 1.41 , covers[2]);
        albumList.add(a3);
        QueuedSong a4 = new QueuedSong("Title 4", 4.00 , covers[3]);
        albumList.add(a4);


        adapter.notifyDataSetChanged();
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
