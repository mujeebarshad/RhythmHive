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

public class ByMe extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private PlaylistListAdapter adapter;
    private List<PlaylistList> albumList;
    private SearchView searchView;
    private Button mButton;

    @SuppressLint("ValidFragment")
    public ByMe(RecyclerView recyclerView, PlaylistListAdapter adapter, List<PlaylistList> albumList) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.albumList = albumList;
    }


    public ByMe() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.byme, container, false);
        searchView=(SearchView)myView.findViewById( R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String sQuery  =""+query;
                Intent i = new Intent (getContext(), SearchedSongList.class);
                i.putExtra("searchedString", sQuery);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return  true;
            }


        });
        mButton=(Button) myView.findViewById(R.id.create);
        mButton.setOnClickListener(this);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view2);
        albumList = new ArrayList<>();
        prepareAlbums();
        adapter = new PlaylistListAdapter(getContext(), albumList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                String selected_playlist = albumList.get(position).getName();
                //Toast.makeText(getContext(), selected_playlist, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), ViewPlaylistSongs.class);
                i.putExtra("selectedPlaylist", selected_playlist);
                startActivity(i);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
        return myView;
    }


    @Override
    public void onClick(View v) {
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(getContext());
        final View mView = getLayoutInflater().inflate(R.layout.playlist_dialog, null);
        Button close=(Button)mView.findViewById(R.id.btnClose);
        mBuilder.setView(mView);
        final android.app.AlertDialog dialog = mBuilder.create();
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button create=(Button)mView.findViewById(R.id.btnCreate);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText input = (EditText) mView.findViewById(R.id.userInput);
                Toast.makeText(getActivity(), input.getText()+ "  created", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void prepareAlbums() {

        PlaylistList a1 = new PlaylistList("Playlist 1", "22-1-2018");
        albumList.add(a1);
        PlaylistList a2 = new PlaylistList("Playlist 2", "11-12-2018");
        albumList.add(a2);
    }


}