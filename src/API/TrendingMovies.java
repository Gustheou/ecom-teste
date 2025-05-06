package API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TrendingMovies {

  private final String API_KEY;

  public TrendingMovies(String apiKey) {
    API_KEY = apiKey;
  }

  public HttpResponse<String> getTrendingMovies(long page) {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.themoviedb.org/3/trending/movie/week?language=pt-br&page="+String.valueOf(page)))
          .header("accept", "application/json")
          .header("Authorization", "Bearer " + API_KEY)
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();
      HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return response;
    } catch (IOException | InterruptedException e) {
      System.out.println("getTrendingMovies Exception: " + e.getMessage());
    }
    return null;
  }
}
