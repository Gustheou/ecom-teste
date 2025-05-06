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

  public String getTopRated(int page) {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.themoviedb.org/3/movie/top_rated?language=pt-br&page="+String.valueOf(page)))
          .header("accept", "application/json")
          .header("Authorization", "Bearer " + API_KEY)
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();
      HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      //System.out.println(response.body());
      JSONParser parse = new JSONParser();
      JSONObject data_obj = (JSONObject) parse.parse(response.body());
      System.out.println("\n===========================================================");

      JSONArray results = (JSONArray) data_obj.get("results");

      for (int i = 0; i < results.size(); i++) {
        JSONObject movie = (JSONObject) results.get(i);
        String title = movie.get("title").toString();
        String releaseDate = movie.get("release_date").toString();
        System.out.println("Title: " + title + ", Release Date: " + releaseDate);
      }
      return response.body();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}
