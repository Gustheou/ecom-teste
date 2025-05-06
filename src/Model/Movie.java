package Model;

import java.util.ArrayList;

public class Movie {

  private String title;
  private String releaseDate;
  private ArrayList<Integer> movieGenre;
  private Double vote_average;
  private String movieID;

  public Movie(String title, ArrayList<Integer> movieGenre, String releaseDate, Double vote_average, String movieID) {
    this.title = title;
    this.movieGenre = movieGenre;
    this.releaseDate = releaseDate;
    this.vote_average = vote_average;
    this.movieID = movieID;
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
