import API.GenreIds;
import API.TopRatedMovies;
import API.TrendingMovies;
import Controller.MovieController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Scanner;


public class Main {

  private final static String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NzE0ZjE2ZGViMjY2YzhjNjcwMDhjMTNjNDZmNjMyZiIsIm5iZiI6MTc0NTM2NTAyNC41NjMsInN1YiI6IjY4MDgyODIwMTVhMWQ1YTYxNGFhOWI1ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ._EFInwczNICamxI8sgJCpOiv7Kj4886iqatH-epac6o";

  public static void main(String[] args) {
    //testTopRated();
    //testGetID();
    //System.out.println(getTrendingMovies());
    //System.out.println(getGenreIDs());
    //MovieController mv = new MovieController(API_KEY);
    //System.out.println(mv.getQuantityForMovieYearTopRated());
    //menu();
    TrendingMovies tr = new TrendingMovies(API_KEY);
    System.out.println(tr.getTrendingMovies(1).body());
  }



  private static void testGetID() {
    GenreIds genreIds = new GenreIds(API_KEY);
    System.out.println(genreIds.getGenreIDs());
  }

  private static String getTrendingMovies() {
    try {
      HttpResponse<String> response;
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.themoviedb.org/3/trending/movie/day?language=pt-BR"))
          .header("accept", "application/json")
          .header("Authorization", "Bearer " + API_KEY)
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      return response.body();
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
        "2 - Quantidade de filme por gênero (top 250);\n" +
        "3 - Quantidade de filme por ano;\n" +
        "4 - Quantos e quais desses filmes estão entre os Trending nos últimos 6 meses\n" +
        "0 - Sair\n" +
        "----------------------------------------------------------\n" +
        "Opção: ");
    int opCode = input.nextInt();
    System.out.println("----------------------------------------------------------");
    MovieController movieController = new MovieController(API_KEY);
    switch (opCode) {
      case 0: {

        break;
      }
      case 1: {
        System.out.println("Exibindo a média de nota por gênero:");
        movieController.getAverageVoteForGenre();
        break;
      }
      case 2: {
        System.out.println("Exibindo quantos filmes de cada gênero existem nos 250 top-rated:");
        movieController.getQuantityForGenreTopRated(250);
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
