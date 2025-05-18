package API;

import java.io.*;
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

  public String getCacheTrendingMovies(long page) {
    try {
      File filePage = new File(".cache/TrendingMovies/page"+ page +".json");
      InputStreamReader reader = new FileReader(filePage);
      BufferedReader bufferedReader = new BufferedReader(reader);
      return bufferedReader.readLine();

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public int getStatusCode() {
    try {
      return getTrendingMovies(1).statusCode();
    }catch (NullPointerException n) {
      System.out.println("Não foi possível se conectar com a API, verifique a conexao com a internet e tente novamente!!!");
    }
    return 0;
  }

  public void cacheThePages(long page){
    File folder = new File(".cache/TrendingMovies/");
    if (!folder.exists()) {
      folder.mkdirs();
    }
    Thread[] pageThread = new Thread[Integer.parseInt(String.valueOf(page))];
    for (int i = 1; i <= page; i++) {
      int pageFileNumber = i;
      pageThread[i-1] = new Thread(() -> {
        File pageFile = new File(folder.getPath()+"/page"+ pageFileNumber +".json");
        String pageInfo = getTrendingMovies(pageFileNumber).body();
        try {
          PrintWriter pw = new PrintWriter(pageFile);
          pw.write(pageInfo);
          pw.close();
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
      });
      pageThread[i-1].start();
    }
  }
}
