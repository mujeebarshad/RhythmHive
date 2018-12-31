package com.example.hp.featuredsongs.activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.hp.featuredsongs.R;
import com.example.hp.featuredsongs.databinding.ActivityMainBinding;
import com.example.hp.featuredsongs.databinding.NavHeaderBinding;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private RecyclerView recyclerView;
    private FeaturedSongAdapter adapter;
    private List<FeaturedSong> albumList;

    private Global nGlobal;
    //data binding
    NavHeaderBinding nBinding;
    ActivityMainBinding mBinding;

    //Video Recording
    private static int VIDEO_REQUEST = 101;
    private Uri videoUri = null;
    private String videoBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        nBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header, mBinding.navView, false);
        mBinding.navView.addHeaderView(nBinding.getRoot());

        Session session = new Session(getApplicationContext());
        nGlobal = new Global();
        nGlobal.uname = session.getusename();
        nBinding.setGlobal(nGlobal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        // Drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(getResources().getColor(R.color.viewBg, this.getTheme()));
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.textcolor, this.getTheme())));
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // /Drawer
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new FeaturedSongAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        FeaturedSong a = new FeaturedSong("True Romance", 2.01 , covers[0]);
        albumList.add(a);

        a = new  FeaturedSong("Xscpae", 2.03, covers[1]);
        albumList.add(a);

        a = new  FeaturedSong("Maroon 5", 3.11, covers[2]);
        albumList.add(a);

        a = new  FeaturedSong("Born to Die", 2.75, covers[3]);
        albumList.add(a);

        a = new  FeaturedSong("Honeymoon", 2.11, covers[4]);
        albumList.add(a);

        a = new  FeaturedSong("I Need a Doctor", 2.00, covers[5]);
        albumList.add(a);

        a = new  FeaturedSong("Loud", 3.09, covers[6]);
        albumList.add(a);

        a = new  FeaturedSong("Legend", 2.87, covers[7]);
        albumList.add(a);

        a = new  FeaturedSong("Hello", 2.33, covers[8]);
        albumList.add(a);

        a = new  FeaturedSong("Greatest Hits", 3.99, covers[9]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home: {
                Intent i = new Intent (getApplicationContext(), com.example.hp.featuredsongs.activities.MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.nav_mymusic: {
                Intent i = new Intent (getApplicationContext(), com.example.hp.featuredsongs.activities.MyMusic.class);
                startActivity(i);
                break;
            }
            case R.id.nav_about: {



                //Log.i("Media Path: ", getpath);
                break;
            }
            case R.id.nav_mood:{

                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
                startActivityForResult(videoIntent, VIDEO_REQUEST);

                break;

//                final AlertDialog.Builder builder;
//                // Volley Login
//
//                final RequestQueue queue = Volley.newRequestQueue(this);
//
//                final String req_url = "http://192.168.100.8:8000/mood";
//
//                queue.start();
//
//                builder = new AlertDialog.Builder(MainActivity.this);
//
//                JsonObjectRequest jsObjRequest = new
//                        JsonObjectRequest(Request.Method.GET,
//                        req_url,
//                        new JSONObject(),
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
////                                try {
////                                    //builder.setTitle("Mood");
////                                    //builder.setMessage(response.getString("message"));
////                                    //AlertDialog alertDialog = builder.create();
////                                    //alertDialog.show();
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        builder.setTitle("Mood");
//                        builder.setMessage("Error in Mood!");
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//                    }
//                });
//                queue.add(jsObjRequest);
//
//        // /Volley Login
//
//                break;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Songs");
       // searchView.setBackgroundColor(Color.BLACK);


        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
               // Toast.makeText(MainActivity.this, ""+query, Toast.LENGTH_SHORT).show();
                String sQuery  =""+query;

                Intent i = new Intent (getApplicationContext(), SearchedSongList.class);

                i.putExtra("searchedString", sQuery);
                startActivity(i);
                return  true;

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_login: {
                Intent i = new Intent (getApplicationContext(), com.example.hp.featuredsongs.activities.Login.class);
                startActivity(i);
                break;
            }
            case R.id.action_sign_up: {
                Intent i = new Intent (getApplicationContext(), com.example.hp.featuredsongs.activities.Signup.class);
                startActivity(i);
                break;
            }
            case R.id.action_now_playing: {
                Intent i = new Intent (getApplicationContext(), com.example.hp.featuredsongs.activities.PlayingSongsActivity.class);
                startActivity(i);
                break;
            }
            case R.id.action_my_music: {
                Intent i = new Intent(getApplicationContext(), com.example.hp.featuredsongs.activities.MyMusic.class);
                startActivity(i);
                break;
            }
        }
        return true;
    }

    public void openProfile(View view){
        Intent i = new Intent (getApplicationContext(), com.example.hp.featuredsongs.activities.Profile.class);
        startActivity(i);
    }
    public void ToggleUser(View view){
        Button btn = (Button) findViewById(R.id.btn_login);
        if(btn.getText().toString() == "Login") {
            Intent i = new Intent(getApplicationContext(), com.example.hp.featuredsongs.activities.Login.class);
            startActivity(i);
        }
        else{
            Session session = new Session(getApplicationContext());
            session.setusename("");
            Intent i = new Intent(getApplicationContext(), com.example.hp.featuredsongs.activities.MainActivity.class);
            startActivity(i);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==VIDEO_REQUEST && resultCode==RESULT_OK) {
            videoUri = data.getData();
        }
        if(data!=null) {
            Uri selectedVideoUri = data.getData();
            String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
            Cursor cursor = managedQuery(selectedVideoUri, projection, null, null, null);

            cursor.moveToFirst();
            String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND);
            // Setting the thumbnail of the video in to the image view
            //msImage.setImageBitmap(thumb);
            InputStream inputStream = null;
            // Converting the video in to the bytes
            try {
                inputStream = getContentResolver().openInputStream(selectedVideoUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int len = 0;
            try {
                while ((len = inputStream.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("converted!");

            String videoData = "";
            //Converting bytes into base64
            videoData = Base64.encodeToString(byteBuffer.toByteArray(), Base64.DEFAULT);
            Log.d("VideoData**>  ", videoData);

            String sinSaltoFinal2 = videoData.trim();
            String sinsinSalto2 = sinSaltoFinal2.replaceAll("\n", "");
            Log.d("VideoData**>  ", sinsinSalto2);

            videoBytes = sinsinSalto2;

            //Sending file

            final String req_url = "http://192.168.100.7:3000/getvideo";

            HashMap<String, String> params = new HashMap<String, String>();
            //Toast.makeText(this, videoBytes.toString(), Toast.LENGTH_SHORT).show();
            params.put("videoUri", String.valueOf(videoBytes));
            //params.put("password", String.valueOf(txtpassword.getText()));

            // Volley

            final RequestQueue queue = Volley.newRequestQueue(this);

            //final String req_url = "http://192.168.100.8:8000/mood";

            queue.start();

            final JsonObjectRequest jsObjRequest = new
                    JsonObjectRequest(Request.Method.POST,
                    req_url,
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                //Delete

                                // /Delete

                                final AlertDialog.Builder builder;
                                // Volley Login

                                final RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());

                                final String req_url = "http://192.168.100.7:8000/mood";

                                queue1.start();

                                builder = new AlertDialog.Builder(MainActivity.this);

                                JsonObjectRequest jsObjRequest = new
                                        JsonObjectRequest(Request.Method.GET,
                                        req_url,
                                        new JSONObject(),
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                try {
                                                    builder.setTitle("Mood");
                                                    builder.setMessage(response.getString("message"));
                                                    AlertDialog alertDialog = builder.create();
                                                    alertDialog.show();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        builder.setTitle("Error");
                                        try {
                                            builder.setMessage(error.toString());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                });
                                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                queue1.add(jsObjRequest);

                        // /Volley Login
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(jsObjRequest);
            // /Sending File
        }

    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}