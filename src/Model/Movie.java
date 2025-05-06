package Model;

import java.util.ArrayList;

public class Movie {

  private String title;
  private String releaseDate;
  private ArrayList<Integer> movieGenre;
  private Double vote_average;

  public Movie(String title, ArrayList<Integer> movieGenre, String releaseDate, Double vote_average) {
    this.title = title;
    this.movieGenre = movieGenre;
    this.releaseDate = releaseDate;
    this.vote_average = vote_average;
  }

  public String getTitle() {
    return title;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public ArrayList<Integer> getMovieGenre() {
    return movieGenre;
  }

  public Double getVoteAverage() {
    return vote_average;
  }
}
