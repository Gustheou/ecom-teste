package Controller;

import API.GenreIds;
import Model.Movie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieController {

  private final String API_KEY;
  private final long PAGE_LIMIT;

  public MovieController(String API_KEY) {
    this.API_KEY = API_KEY;
    PAGE_LIMIT = getTotalPages();
  }

  private List<Movie> getMoviesUntilPage (long page) {
    ArrayList<Movie> moviesList = new ArrayList<Movie>();
    for (int i = 1; i <= page; i++) {
      try {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.themoviedb.org/3/movie/top_rated?language=pt-br&page="+String.valueOf(page)))
            .header("accept", "application/json")
            .header("Authorization", "Bearer " + API_KEY)
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONParser parse = new JSONParser();
        JSONObject data_obj = (JSONObject) parse.parse(response.body());
        JSONArray results = (JSONArray) data_obj.get("results");
        for (Object movieObject : results) {
          JSONObject movieJson = (JSONObject) movieObject;
          String title = movieJson.get("title").toString();
          String releaseDate = movieJson.get("release_date").toString();
          ArrayList<Integer> genreIds = (ArrayList<Integer>) movieJson.get("genre_ids");
          Double voteAverage = (double) movieJson.get("vote_average");
          Movie movie = new Movie(title, genreIds, releaseDate, voteAverage);
          moviesList.add(movie);
        }
      } catch (IOException | InterruptedException e) {
        System.out.println("Exception in getMoviesUntilPage: " + e.getMessage());
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }
    return moviesList;
  }

  /*#################################
  Média de Nota por Gênero – 5 pontos
    - Calcular e exibir a nota média dos filmes para cada gênero (ex: Ação: 7.9, Drama: 8.2...)
   ##################################
  */

  public HashMap<String, Double> getAverageVoteForGenre () {
    HashMap<String, Double> averageVoteForGenre = new HashMap<>();
    HashMap<Object, String> genreMap = new GenreIds(API_KEY).getGenreIDs();
    List<Movie> movieList = getMoviesUntilPage(PAGE_LIMIT); //CHANGE IT LATER
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
      averageVoteForGenre.put(genre_name, average);
    }
    return averageVoteForGenre;
  }

  private boolean checkGenreMatch(Object expected, ArrayList matches) {
    for (Object match : matches) {
      if (expected == match) {
        return true;
      }
    }
    return false;
  }

  private Long getTotalPages() {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.themoviedb.org/3/movie/top_rated?language=pt-br&page=1"))
          .header("accept", "application/json")
          .header("Authorization", "Bearer " + API_KEY)
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();
      HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      JSONParser parse = new JSONParser();
      JSONObject data_obj = (JSONObject) parse.parse(response.body());
      return (Long) data_obj.get("total_pages");
    } catch (InterruptedException | IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }


  /*#################################
   Quantidade de Filmes por Gênero – 3 pontos
    - Mostrar quantos filmes de cada gênero existem nos 250 top-rated
   ##################################
   */

  /*#################################
  Quantidade de Filmes por Ano – 4 pontos
  ###################################
  */
}
