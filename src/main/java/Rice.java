import java.util.Scanner;


public class Rice {

    static int currEmptyPos = 0;

    public static void main(String[] args) {
        Task[] list = new Task[100];

        String message = "Hello! I'm Rice.\n"
                + "What can I do for you?\n";

        System.out.println(message);
        
        Scanner scanner = new Scanner(System.in);
        try {
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
              if (userInput.split(" ").length < 2) { //task number is not passed
                throw new RiceException("There is no task specified");
              }

              int taskNumber = Integer.parseInt(userInput.split(" ")[1]);

              if (taskNumber > currEmptyPos) { //task number is out of range
                throw new RiceException("Invalid task number"); 
              }

              list[taskNumber - 1].mark();
              System.out.println("  Nice! I've marked this task as done:");
              System.out.println("    " + list[taskNumber - 1].toString());
          } else if (userInput.split(" ")[0].equals("unmark")) {

              if (userInput.split(" ").length < 2) { //task number is not passed
                throw new RiceException("There is no task specified");
              }

              int taskNumber = Integer.parseInt(userInput.split(" ")[1]);

              if (taskNumber > currEmptyPos) { //task number is out of range
                throw new RiceException("Invalid task number"); 
              }

              list[taskNumber - 1].unmark();
              System.out.println("  OK, I've marked this task as not done yet:");
              System.out.println("    " + list[taskNumber - 1].toString());
          } else if (userInput.split(" ", 2)[0].equals("todo")) {
            if (userInput.split(" ", 2).length < 2) { //desc is empty
              throw new RiceException("OOPS!!! The decription of todo cannot be empty");
            }
            Todo task = new Todo(userInput.split(" ", 2)[1]);
            list[currEmptyPos] = task;
            currEmptyPos += 1;
            System.out.println("Got it. I've added this task:\n" + task.toString());
            System.out.println(String.format("Now you have %d tasks in the list", currEmptyPos));
          } else if (userInput.split(" ")[0].equals("deadline")) {

            if (userInput.split(" ").length < 2) { //task is empty
              throw new RiceException("OOPS!!! The decription of deadline cannot be empty");
            }

            if (userInput.split(" ", 2)[1].split("/").length < 2){ //missing deadline
              throw new RiceException("OOPS! There are some empty parameters");
            }

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

            if (userInput.split(" ").length < 2) { //missing task
              throw new RiceException("OOPS!!! The decription of event cannot be empty");
            }

            if (userInput.split(" ",2)[1].split("/").length < 3) { //missing from or to 
              throw new RiceException("OOPS!!! There are some empty parameters");
            }

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
            } else { //catch all invalid Task objects
            throw new RiceException("OOPS!!! I'm sorry, but I don't know whaat that means :-(");
            }
          }
        } catch (RiceException e){
          System.out.println(e.getMessage());
        }
    }
}
