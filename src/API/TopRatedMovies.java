package API;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TopRatedMovies {

  private final String API_KEY;

  public TopRatedMovies(String apiKey) {
    API_KEY = apiKey;
  }

  public HttpResponse<String> getTopRated(long page) {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.themoviedb.org/3/movie/top_rated?language=pt-br&page="+String.valueOf(page)))
          .header("accept", "application/json")
          .header("Authorization", "Bearer " + API_KEY)
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();
      HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return response;
    } catch (IOException | InterruptedException e) {
      System.out.println("getTopRated Exception: " + e.getMessage());
    }
    return null;
  }

  public Long getTotalPages() {
    try {
      HttpResponse<String> response = new TopRatedMovies(API_KEY).getTopRated(1);
      JSONParser parse = new JSONParser();
      JSONObject data_obj = (JSONObject) parse.parse(response.body());
      return (Long) data_obj.get("total_pages");
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
