package com.example.tanushreechaubal.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PopularMoviesDetailsActivity extends AppCompatActivity {

    private TextView titleTextView;
    private ImageView imageView;
    private TextView releaseDateTextView;
    private TextView voteAverageTextView;
    private TextView plotSynopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_details);

        titleTextView = findViewById(R.id.textView_title);
        imageView = findViewById(R.id.grid_item_image);
        releaseDateTextView = findViewById(R.id.textView_releaseDate);
        voteAverageTextView = findViewById(R.id.textView_voteAvg);
        plotSynopsisTextView = findViewById(R.id.textView_plotSynopsis);

        String title = getIntent().getStringExtra("title");
        titleTextView.setText(title);
        String release_date = getIntent().getStringExtra("release_date");
        String plot_synopsis = getIntent().getStringExtra("plot_synopsis");
        releaseDateTextView.setText(plot_synopsis);
        plotSynopsisTextView.setText(release_date);
        String image = getIntent().getStringExtra("poster");
        Picasso.with(this).load(image).into(imageView);
        Double vote_avg = getIntent().getDoubleExtra("vote_average", 0);
        voteAverageTextView.setText(vote_avg.toString());

    }

}
