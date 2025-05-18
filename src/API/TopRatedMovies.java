package API;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TopRatedMovies {

  private final String API_KEY;
  private final String CACHE_PATH = ".cache/TopRatedMovies/";

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

  public String getCacheTopRated(long page) {
    try {
      File filePage = new File(CACHE_PATH+"page"+ page +".json");
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
      return getTopRated(1).statusCode();
    }catch (NullPointerException n) {
      System.out.println("Não foi possível se conectar com a API, verifique a conexao com a internet e tente novamente!!!");
    }
    return 0;
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

  public void cacheThePages(long page){
    File folder = new File(CACHE_PATH);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    Thread[] pageThread = new Thread[Integer.parseInt(String.valueOf(page))];
    for (int i = 1; i <= page; i++) {
      int pageFileNumber = i;
      pageThread[i-1] = new Thread(() -> {
        File pageFile = new File(folder.getPath()+"/page"+ pageFileNumber +".json");
        String pageInfo = getTopRated(pageFileNumber).body();
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
