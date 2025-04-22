import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App {
  public static void main(String[] args) throws Exception {
    System.out.println(a());

  }

private static HttpResponse a () throws IOException, InterruptedException {
  HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1"))
    .header("accept", "application/json")
    .method("GET", HttpRequest.BodyPublishers.noBody())
    .build();
  HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
  System.out.println(response.body());
  return response;
}

}
