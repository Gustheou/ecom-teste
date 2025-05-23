package Controller;

import API.GenreIds;
import API.TopRatedMovies;
import API.TrendingMovies;
import Model.Movie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.*;

public class MovieController {

  private final String API_KEY;
  //private final long PAGE_LIMIT;
  private final int CACHED_TOPRATED_PAGES = 18;
  private final int CACHED_TRENDINGMOVIES_PAGES=25;

  public MovieController(String API_KEY) {
    this.API_KEY = API_KEY;
    //PAGE_LIMIT = new TopRatedMovies(API_KEY).getTotalPages();
  }

  private List<Movie> getMoviesTopRatedUntilPage (long page) {
    ArrayList<Movie> moviesList = new ArrayList<Movie>();
    for (int i = 1; i <= page; i++) {
      try {
        HttpResponse<String> response = new TopRatedMovies(API_KEY).getTopRated(i);
        JSONParser parse = new JSONParser();
        JSONObject data_obj = (JSONObject) parse.parse(response.body());
        JSONArray results = (JSONArray) data_obj.get("results");
        System.out.println(response.body());
        System.out.println("###############");
        for (Object movieObject : results) {
          JSONObject movieJson = (JSONObject) movieObject;
          Movie movie = movieData(movieJson);
          moviesList.add(movie);
        }
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }
    return moviesList;
  }

  private List<Movie> getMoviesTopRatedUntilMovieQuantity (int limit) {
    ArrayList<Movie> moviesList = new ArrayList<Movie>();
    int counter = 0;
    File[] cachedFiles = new File(".cache/TopRatedMovies/").listFiles();
    if(cachedFiles!= null && cachedFiles.length == CACHED_TOPRATED_PAGES) {
      for(int i = 1; i <= CACHED_TOPRATED_PAGES; i++) {
        try {
          String response = new TopRatedMovies(API_KEY).getCacheTopRated(i);
          JSONParser parse = new JSONParser();
          JSONObject data_obj = (JSONObject) parse.parse(response);
          JSONArray results = (JSONArray) data_obj.get("results");
          for (Object movieObject : results) {
            JSONObject movieJson = (JSONObject) movieObject;
            Movie movie = movieData(movieJson);
            moviesList.add(movie);
            counter+=1;
            if (counter == limit) {
              return moviesList;
            }
          }
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
    } else {
      new TopRatedMovies(API_KEY).cacheThePages(CACHED_TOPRATED_PAGES);
      return getMoviesTopRatedUntilMovieQuantity(limit);
    }
    return null;
  }

  private List<Movie> getMovieTrendingList(long limitPage) {
    ArrayList<Movie> moviesList = new ArrayList<Movie>();
    File[] cachedFiles = new File(".cache/TrendingMovies/").listFiles();
    if(cachedFiles!= null && cachedFiles.length == CACHED_TRENDINGMOVIES_PAGES) {
      for (int i = 1; i <= limitPage; i++) {
        try {
          String response = new TrendingMovies(API_KEY).getCacheTrendingMovies(i);
          JSONParser parse = new JSONParser();
          JSONObject data_obj = (JSONObject) parse.parse(response);
          JSONArray results = (JSONArray) data_obj.get("results");
          for (Object movieObject : results) {
            JSONObject movieJson = (JSONObject) movieObject;
            Movie movie = movieData(movieJson);
            moviesList.add(movie);
          }
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
      return moviesList;
    } else {
      new TrendingMovies(API_KEY).cacheThePages(CACHED_TRENDINGMOVIES_PAGES);
      return getMovieTrendingList(limitPage);
    }
  }

  private boolean checkGenreMatch(Object expected, ArrayList matches) {
    for (Object match : matches) {
      if (expected == match) {
        return true;
      }
    }
    return false;
  }

  private Movie movieData(JSONObject movieJson){
    String title = movieJson.get("title").toString();
    String releaseDate = movieJson.get("release_date").toString();
    ArrayList<Integer> genreIds = (ArrayList<Integer>) movieJson.get("genre_ids");
    Double voteAverage = (double) movieJson.get("vote_average");
    String movieID = movieJson.get("id").toString();
    return new Movie(title, genreIds, releaseDate, voteAverage, movieID);
  }



  /*#################################
  Média de Nota por Gênero – 5 pontos
    - Calcular e exibir a nota média dos filmes para cada gênero (ex: Ação: 7.9, Drama: 8.2...)
   ##################################
  */

  public HashMap<String, Double> getAverageVoteForGenre (int totalOfMovies) {
    final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    decimalFormat.setRoundingMode(RoundingMode.UP);
    HashMap<String, Double> averageVoteForGenre = new HashMap<>();
    HashMap<Object, String> genreMap = new GenreIds(API_KEY).getGenreIDs();
    List<Movie> movieList = getMoviesTopRatedUntilMovieQuantity(totalOfMovies);
    for(Object id : genreMap.keySet()) {
      int movieCounter = 0;
      double averageSum = 0;
      for (Movie movie : movieList) {
        if (checkGenreMatch(id, movie.getMovieGenre())) {
          movieCounter++;
          averageSum += movie.getVoteAverage();
        }
      }
      String genre_name = genreMap.get(id);
      Double average = averageSum/movieCounter;
      average = Double.parseDouble(decimalFormat.format(average));
      averageVoteForGenre.put(genre_name, average);
    }
    return averageVoteForGenre;
  }


  /*#################################
   Quantidade de Filmes por Gênero – 3 pontos
    - Mostrar quantos filmes de cada gênero existem nos 250 top-rated
   ##################################
   */

  public HashMap<String, Integer> getQuantityForGenreTopRated (int totalOfMovies) {
    HashMap<String, Integer> movieQuantityForGenre = new HashMap<>();
    HashMap<Object, String> genreMap = new GenreIds(API_KEY).getGenreIDs();
    List<Movie> movieList = getMoviesTopRatedUntilMovieQuantity(totalOfMovies);
    for(Object id : genreMap.keySet()) {
      int movieCounter = 0;
      for (Movie movie : movieList) {
        if (checkGenreMatch(id, movie.getMovieGenre())) {
          movieCounter++;
        }
      }
      String genre_name = genreMap.get(id);
      movieQuantityForGenre.put(genre_name, movieCounter);
    }
    return movieQuantityForGenre;
  }



  /*#################################
  Quantidade de Filmes por Ano – 4 pontos
  ###################################
  */

  public HashMap<String, Integer> getQuantityForMovieYearTopRated (int totalOfMovies) {
    HashMap<String, Integer> movieQuantityForMovieYear = new HashMap<>();
    List<Movie> movieList = getMoviesTopRatedUntilMovieQuantity(totalOfMovies);
    final int YEAR = 0;
    for (Movie movie: movieList) {
      String[] movieDate = movie.getReleaseDate().split("-");
      if (movieQuantityForMovieYear.get(movieDate[YEAR]) != null) {
        int newNumber = movieQuantityForMovieYear.get(movieDate[YEAR]);
        newNumber+=1;
        movieQuantityForMovieYear.replace(movieDate[YEAR], newNumber);
      } else {
        movieQuantityForMovieYear.put(movieDate[YEAR],1);
      }
    }
    return movieQuantityForMovieYear;
  }



  /*#################################
  Quantos e quais desses filmes estão entre os Trending nas 20 primeiras páginas – 5 pontos
    Obter os filmes em alta (trending-movies) nas 20 primeiras páginas de uma consulta SEMANAL (week)
    Verificar quantos dos top 250 aparecem nos trendings
    Exibir essa quantidade e listar os nomes
  ###################################
  */

  public List<String> getMovieTopRatedAndTrending() {
    List<String> movieTopRatedAndTrending = new ArrayList<>();
    List<Movie> moviesTopRated = getMoviesTopRatedUntilMovieQuantity(250);
    List<Movie> moviesTrendingList = getMovieTrendingList(20);
    for(Movie movieTopRated : moviesTopRated) {
      for(Movie movieTrending : moviesTrendingList) {
        if (movieTopRated.getMovieID().equals(movieTrending.getMovieID())) {
          movieTopRatedAndTrending.add(movieTopRated.getTitle());
        }
      }
    }
    return movieTopRatedAndTrending;
  }
}
