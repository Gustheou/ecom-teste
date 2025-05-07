import Controller.MovieController;


import java.util.*;


public class Main {

  private final static String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NzE0ZjE2ZGViMjY2YzhjNjcwMDhjMTNjNDZmNjMyZiIsIm5iZiI6MTc0NTM2NTAyNC41NjMsInN1YiI6IjY4MDgyODIwMTVhMWQ1YTYxNGFhOWI1ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ._EFInwczNICamxI8sgJCpOiv7Kj4886iqatH-epac6o";

  public static void main(String[] args) {
    menu();
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
      case 1: { //Média de nota por gênero
        System.out.println("Exibindo a média de nota por gênero:");
        HashMap<String, Double> averageGenre = movieController.getAverageVoteForGenre();
        System.out.println(averageGenre);
        List<String> averageTitleGenre = new ArrayList<>(averageGenre.keySet());
        List<Double> averageValueGenre = new ArrayList<>(averageGenre.values());
        showOnConsole("GÊNERO", "MÉDIA");
        for (int i = 0 ; i < averageGenre.size(); i++) {
          System.out.printf("%-20s %-10s %-20s %-20s\n", "| "+averageTitleGenre.get(i),"|",averageValueGenre.get(i), "|");
        }
        System.out.print("|----------------------------------------------------|\n");

        menu();
        break;
      }
      case 2: {//Quantidade de filme por gênero (top 250)
        System.out.println("Exibindo quantos filmes de cada gênero existem nos 250 top-rated:");
        HashMap<String, Integer> genreQuantity = movieController.getQuantityForGenreTopRated(250);
        List<String> quantityTitleGenre = new ArrayList<>(genreQuantity.keySet());
        List<Integer> quantityValueGenre = new ArrayList<>(genreQuantity.values());
        showOnConsole("GÊNERO", "QUANTIDADE");
        for (int i = 0 ; i < genreQuantity.size(); i++) {
          System.out.printf("%-20s %-10s %-20s %-20s\n", "| "+quantityTitleGenre.get(i),"|",quantityValueGenre.get(i), "|");
        }
        System.out.print("|----------------------------------------------------|\n");

        menu();
        break;
      }
      case 3: {//Quantidade de filme por ano
        System.out.println("Exibindo quantos filmes por ano de lançamento existem.");
        HashMap<String,Integer> moviesReleaseDate = movieController.getQuantityForMovieYearTopRated();
        List<String> dateSorted = new ArrayList(moviesReleaseDate.keySet());
        Collections.sort(dateSorted);
        showOnConsole("ANO", "QUANTIDADE");
        for (int i = 0 ; i < moviesReleaseDate.size(); i++) {
          System.out.printf("%-20s %-10s %-20s %-20s\n", "| "+dateSorted.get(i),"|",moviesReleaseDate.get(dateSorted.get(i)), "|");
        }
        System.out.print("|----------------------------------------------------|\n");

        menu();
        break;
      }
      case 4: {//Quantos e quais desses filmes estão entre os Trending nos últimos 6 meses

        break;
      }
      default: {
        System.out.println("Opção inválida!!! Tente novamente.");
        menu();
        break;
      }
    }
  }

  private static void showOnConsole(String title1, String title2) {
    System.out.print("|----------------------------------------------------|\n");
    System.out.printf("%-20s %-10s %-20s %-10s\n","| "+title1,"|",title2, "|");
    System.out.print("|----------------------------------------------------|\n");
  }
}
