import java.util.Scanner;


public class Rice {

    static int currEmptyPos = 0;

    public static void main(String[] args) {
        Task[] list = new Task[100];

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
              System.out.println(String.format("%d.%s", i + 1, list[i]));
            }
          } else if (userInput.split(" ")[0].equals("mark")) { //split string, userInput should be immutable so this is another copy
              int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
              list[taskNumber - 1].mark();
              System.out.println("  Nice! I've marked this task as done:");
              System.out.println("    " + list[taskNumber - 1].toString());
          } else if (userInput.split(" ")[0].equals("unmark")) { 
              int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
              list[taskNumber - 1].unmark();
              System.out.println("  OK, I've marked this task as not done yet:");
              System.out.println("    " + list[taskNumber - 1].toString());
          } else if (userInput.split(" ", 2)[0].equals("todo")) {
            Todo task = new Todo(userInput.split(" ", 2)[1]);
            list[currEmptyPos] = task;
            currEmptyPos += 1;
            System.out.println("Got it. I've added this task:\n" + task.toString());
            System.out.println(String.format("Now you have %d tasks in the list", currEmptyPos));
          } else if (userInput.split(" ")[0].equals("deadline")) {
            String[] newString = userInput.split(" ", 2)[1]
                                          .split("/");

            String task = newString[0];
            String end = newString[1].split(" ", 2)[1]; 

            Deadline deadline = new Deadline(task, end);
            list[currEmptyPos] = deadline;
            currEmptyPos += 1;
            System.out.println("Got it. I've added this task:\n" + deadline.toString());
            System.out.println(String.format("Now you have %d tasks in the list", currEmptyPos));
          } else if (userInput.split(" ")[0].equals("event")) {
            String[] newString = userInput.split(" ",2)[1]
                                          .split("/");

            String task = newString[0];
            String from = newString[1].split(" ", 2)[1];
            String to = newString[2].split(" ", 2)[1];

            Events event = new Events(task, from, to);
            list[currEmptyPos] = event;
            currEmptyPos += 1;
            System.out.println("Got it. I've added this task:\n" + event.toString());
            System.out.println(String.format("Now you have %d tasks in the list", currEmptyPos));

          } else {
              list[currEmptyPos] = new Task(userInput);
              currEmptyPos += 1;
              System.out.println("  added: " + userInput);
              System.out.println(String.format("Now you have %d tasks in the list", currEmptyPos));
            }
        }
    }
}
