package com.example.tanushreechaubal.popularmovies;

import android.text.TextUtils;
import android.util.Log;

import com.example.tanushreechaubal.popularmovies.data.GridItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PopularMoviesUtils {

        public static final String LOG_TAG = PopularMoviesMainActivity.class.getName();

        private PopularMoviesUtils(){

        }

        public static List<GridItem> fetchMovieData(String requestURL) {

            URL url = createUrl(requestURL);
            Log.d(LOG_TAG, "TEST: fetchMovieData............"+ requestURL);

            String jsonResponse = null;
            try{
                jsonResponse = makeHttpRequest(url);
            }catch(IOException e){
                Log.e(LOG_TAG, "Problem making HTTP request", e);
            }

            List<GridItem> gridItems = extractMoviesDetailsFromJSON(jsonResponse);
            return gridItems;
        }

        public static List<GridItem> extractMoviesDetailsFromJSON(String MovieJSON){

            if (TextUtils.isEmpty(MovieJSON)){
                return null;
            }

            List<GridItem> movies = new ArrayList<>();

            try{
                Log.e(LOG_TAG, "TEST: This is MOVIEJSON:  "+MovieJSON);
                JSONObject root = new JSONObject(MovieJSON);
                Integer page = root.getInt("page");
                JSONArray movie_array = root.getJSONArray("results");

                for(int i=0; i<movie_array.length();i++){
                    JSONObject movieObject = movie_array.getJSONObject(i);
                    String posterPath = "http://image.tmdb.org/t/p/w185" + movieObject.getString("poster_path");
                    String movie_title = movieObject.getString("title");
                    String movie_release_date = movieObject.getString("release_date");
                    String movie_overview = movieObject.getString("overview");
                    Double movie_vote_avg = movieObject.getDouble("vote_average");
                    GridItem gridList = new GridItem(posterPath, movie_title, movie_vote_avg, movie_overview, movie_release_date);
                    movies.add(gridList);
                }
            } catch(JSONException e){
                Log.e("PopularMoviesUtils", "Problem parsing the movie JSON results", e);
            }
            return movies;
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private static URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Problem building the URL ", e);
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private static String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";

            // If the URL is null, then return early.
            if (url == null) {
                return jsonResponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                Log.e(LOG_TAG, "TEST: this is the URL:    "+url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // If the request was successful (response code 200),
                // then read the input stream and parse the response.
                if (urlConnection.getResponseCode() == 200) {
                    Log.e(LOG_TAG, "TEST: this is the URL:    "+url);

                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem retrieving movie JSON results.", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // Closing the input stream could throw an IOException, which is why
                    // the makeHttpRequest(URL url) method signature specifies than an IOException
                    // could be thrown.
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private static String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
}
