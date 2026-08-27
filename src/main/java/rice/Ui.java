package rice;
import java.util.Scanner;

public class Ui {
  private final Scanner scanner = new Scanner(System.in);
  
  public void greet() {
    String message = "Hello! I'm Rice.\n"
                + "What can I do for you?\n";
    System.out.println(message);
    System.out.println("Dates should be recorded in the format YYYY-MM-DD");
  }


  public String readInput() {
      String userInput = scanner.nextLine();
      return userInput;
  }

  public void show(String message) { System.out.println(message); }
}
