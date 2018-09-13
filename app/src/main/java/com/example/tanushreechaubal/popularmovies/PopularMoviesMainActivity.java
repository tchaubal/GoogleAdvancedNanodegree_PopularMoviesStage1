package com.example.tanushreechaubal.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.example.tanushreechaubal.popularmovies.GridViewAdapter;
import com.example.tanushreechaubal.popularmovies.data.GridItem;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PopularMoviesMainActivity extends AppCompatActivity {
    private GridViewAdapter gridViewAdapter;
    private TextView emptyStateTextView;
    private ArrayList<GridItem> mGridData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies_main);

        emptyStateTextView = findViewById(R.id.emptyState_TextView);

        GridView gridView = findViewById(R.id.gridView);
        mGridData = new ArrayList<>();
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridItem item = (GridItem) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(PopularMoviesMainActivity.this, PopularMoviesDetailsActivity.class);
                intent.putExtra("title",item.getMovie_title());
                intent.putExtra("release_date", item.getRelease_date());
                intent.putExtra("plot_synopsis", item.getPlot_synopsis());
                intent.putExtra("vote_average", item.getVote_average());
                intent.putExtra("poster", item.getImage());
                startActivity(intent);
            }
        });

        try {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                emptyStateTextView.setText("");
                MoviesAsyncTask task = new MoviesAsyncTask();
                String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=ADD_API_KEY_HERE";
                Uri builtURI = Uri.parse(MOVIE_BASE_URL);
                URL requestURL = new URL(builtURI.toString());
                String queryMovies = requestURL.toString();
                task.execute(queryMovies);
            } else {
                gridViewAdapter.clear();
                emptyStateTextView.setText(R.string.textView_error_message_no_internet);
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_mostPopular){
            //sort grid items by most popular

            try {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    emptyStateTextView.setText("");
                    MoviesAsyncTask task = new MoviesAsyncTask();
                    String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=ADD_API_KEY_HERE";
                    Uri builtURI = Uri.parse(MOVIE_BASE_URL);
                    URL requestURL = new URL(builtURI.toString());
                    String queryMovies = requestURL.toString();
                    task.execute(queryMovies);
                } else {
                    gridViewAdapter.clear();
                    emptyStateTextView.setText(R.string.textView_error_message_no_internet);
                }
            } catch (MalformedURLException e){
                e.printStackTrace();
            }
        }

        if (id == R.id.menu_highestRated) {
            //sort grid items by highest rated
            try {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    emptyStateTextView.setText("");
                    MoviesAsyncTask task = new MoviesAsyncTask();
                    String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=ADD_API_KEY_HERE";
                    Uri builtURI = Uri.parse(MOVIE_BASE_URL);
                    URL requestURL = new URL(builtURI.toString());
                    String queryMovies = requestURL.toString();
                    task.execute(queryMovies);
                } else {
                    gridViewAdapter.clear();
                    emptyStateTextView.setText(R.string.textView_error_message_no_internet);
                }
            } catch (MalformedURLException e){
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){

            Log.e("On Config Change","LANDSCAPE");
        }else{

            Log.e("On Config Change","PORTRAIT");
        }
    }

    private class MoviesAsyncTask extends AsyncTask<String, Void, List<GridItem>> {

        @Override
        protected List<GridItem> doInBackground(String... params) {
            if(params.length < 1 || params[0] == null){
                return null;
            }
            List<GridItem> result = PopularMoviesUtils.fetchMovieData(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<GridItem> movies) {
            gridViewAdapter.clear();
            if(movies != null && !movies.isEmpty()){
                emptyStateTextView.setText("");
                gridViewAdapter.addAll(movies);
            } else {
                gridViewAdapter.clear();
                emptyStateTextView.setText(R.string.textView_error_message_no_data);
            }
        }
    }
}
