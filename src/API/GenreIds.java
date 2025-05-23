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
import java.util.HashMap;

public class GenreIds {

  private final String API_KEY;

  public GenreIds(String apiKey) {
    API_KEY = apiKey;
  }

  public HashMap<Object, String> getGenreIDs() {
    try {
      HttpResponse<String> response = getAPIResponse();
      JSONParser parse = new JSONParser();
      JSONObject dataObject = (JSONObject) parse.parse(response.body());
      JSONArray genreContent = (JSONArray) dataObject.get("genres");
      HashMap<Object, String> genreIdentification = new HashMap<Object, String>();
      for (Object genre : genreContent) {
        JSONObject movie = (JSONObject) genre;
        Object id = movie.get("id");
        String title = movie.get("name").toString();
        genreIdentification.put(id, title);
      }
      return genreIdentification;
    } catch (ParseException e) {
      System.out.println("Exception in getGenreIDS: " + e.getMessage());
    }
    return null;
  }

  public int getStatusCode() {
    try {
      HttpResponse<String> response = getAPIResponse();
      return response.statusCode();
    } catch (NullPointerException e) {
      System.out.println("Não foi possível se conectar com a API, verifique a conexao com a internet e tente novamente!!!");
    }
    return 1;
  }

  private HttpResponse<String> getAPIResponse() {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.themoviedb.org/3/genre/movie/list?language=pt-br"))
          .header("accept", "application/json")
          .header("Authorization", "Bearer " + API_KEY)
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();
      return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      System.out.println("Exception in getAPIResponse: "+ e.getMessage());    }
      return null;
  }
}
