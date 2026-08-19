import java.util.Scanner;


public class Rice {

    static int currEmptyPos = 0;

    public static void main(String[] args) {
        String[] list = new String[100];

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

          if (userInput.equals("list")) {
            for (int i = 0; i < currEmptyPos; i += 1) {
                System.out.println(String.format("  %d. %s", i + 1, list[i]));
            }
          } else {
            list[currEmptyPos] = userInput;
            currEmptyPos += 1;
            System.out.println("  added: " + userInput);
            }
        }
    }
}
