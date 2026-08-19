import java.util.Scanner;


public class Rice {
    public static void main(String[] args) {
        String message = "Hello! I'm Rice.\n"
                + "What can I do for you?\n";
        System.out.println(message);
        
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
          String userInput = scanner.nextLine();
          if (userInput.equals("bye")) {
            System.out.println("  Bye. Hope to see you again soon!");
            break;
          }
            System.out.println("  " + userInput);
        }
    }
}
