import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    // getTopRated();
    // System.out.println(getTrendingMovies());
    menu();
  }

  private final static String API_READ = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NzE0ZjE2ZGViMjY2YzhjNjcwMDhjMTNjNDZmNjMyZiIsIm5iZiI6MTc0NTM2NTAyNC41NjMsInN1YiI6IjY4MDgyODIwMTVhMWQ1YTYxNGFhOWI1ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ._EFInwczNICamxI8sgJCpOiv7Kj4886iqatH-epac6o";

  private static HttpResponse getTopRated() {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1"))
          .header("accept", "application/json")
          .header("Authorization", "Bearer " + API_READ)
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();
      HttpResponse<String> response;
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
      return response;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  private static HttpResponse getTrendingMovies() {
    try {
      HttpResponse<String> response;
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.themoviedb.org/3/trending/movie/day?language=en-US"))
          .header("accept", "application/json")
          .header("Authorization", "Bearer " + API_READ)
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
      return response;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  private static void menu() {
    Scanner input = new Scanner(System.in);
    System.out.print("----------------------------------------------------------\n" +
        "Ola, seja bem vindo ao sistema, escolha uma opção: \n" +
        "1 - Média de nota por gênero;\n" +
        "2 - Quantidade de filme por gênero;\n" +
        "3 - Quantidade de filme por ano;\n" +
        "4 - Quantos e quais desses filmes estão entre os Trending nos últimos 6 meses\n" +
        "0 - Sair\n" +
        "----------------------------------------------------------\n" +
        "Opção: ");
    int opCode = input.nextInt();
    System.out.println("----------------------------------------------------------");
    switch (opCode) {
      case 0: {

        break;
      }
      case 1: {

        break;
      }
      case 2: {

        break;
      }
      case 3: {

        break;
      }
      case 4: {

        break;
      }
      default: {
        System.out.println("Opção inválida!!! Tente novamente.");
        menu();
        break;
      }
    }
  }
}
