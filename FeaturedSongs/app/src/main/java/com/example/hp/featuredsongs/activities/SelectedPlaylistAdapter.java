package com.example.hp.featuredsongs.activities;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.hp.featuredsongs.R;

import java.util.Date;
import java.util.List;

public class SelectedPlaylistAdapter extends RecyclerView.Adapter<SelectedPlaylistAdapter.MyViewHolder>  {

    private Context mContext;
    private List<PlaylistList> albumList;
    private RecyclerViewClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,date;
        public ImageView  overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
        }
    }


    public SelectedPlaylistAdapter(Context mContext, List<PlaylistList> albumList, RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.albumList = albumList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_playlist_row, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PlaylistList complete_playlist = albumList.get(position);
        holder.title.setText(complete_playlist.getName());
        holder.date.setText(complete_playlist.getDate());


    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}