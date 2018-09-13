package com.example.tanushreechaubal.popularmovies.data;

public class GridItem {

    private String image;
    private String movie_title;
    private Double vote_average;
    private String release_date;
    private String plot_synopsis;

    public GridItem(String poster_path, String title, Double vote, String date, String plot) {
        super();
        image = poster_path;
        movie_title = title;
        vote_average = vote;
        release_date = date;
        plot_synopsis = plot;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }
}
