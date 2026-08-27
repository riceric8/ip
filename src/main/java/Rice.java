import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;



public class Rice {

    static int taskCounter = 0;

    enum Command {
      bye, list, mark, unmark, todo, deadline, event, delete;

      static Command createCommand(String command) throws RiceException {
        try {
          return Command.valueOf(command);
        } catch (IllegalArgumentException e) {
          throw new RiceException("OOPS!!! I'm sorry, but I don't know whaat that means :-(");
        }
      }
    }

    public static void main(String[] args) {
        List<Task> list = new ArrayList<>();
        Storage localList = new Storage("data", "listOfTasks.txt"); //specify relative path
        TaskParser taskParser = new TaskParser(); 
        String message = "Hello! I'm Rice.\n"
                + "What can I do for you?\n";

        System.out.println(message);
        List<String> legacyList = localList.load();
        list = taskParser.parseTasks(legacyList);
        taskCounter = list.size();
        
        Scanner scanner = new Scanner(System.in);
        try {
          while (scanner.hasNextLine()) {
          String userInput = scanner.nextLine();

          Command command = Command.createCommand(userInput.split(" ")[0]);
          
          switch (command) {

          case bye -> {
            System.out.println("  Bye. Hope to see you again soon!");
            System.exit(0);
          }

          case list -> {
            for (int i = 0; i < taskCounter; i += 1) {
              System.out.println(String.format("%d.%s", i + 1, list.get(i)));
            }
            break;
          }

          case mark -> { //split string, userInput should be immutable so this is another copy
              if (userInput.split(" ").length < 2) { //task number is not passed
                throw new RiceException("There is no task specified");
              }

              int taskNumber = Integer.parseInt(userInput.split(" ")[1]);

              if (taskNumber > taskCounter) { //task number is out of range
                throw new RiceException("Invalid task number"); 
              }

              list.get(taskNumber - 1).mark();
              System.out.println("  Nice! I've marked this task as done:");
              System.out.println("    " + list.get(taskNumber - 1).toString());
              break;
          }

          case unmark -> {
              if (userInput.split(" ").length < 2) { //task number is not passed
                throw new RiceException("There is no task specified");
              }

              int taskNumber = Integer.parseInt(userInput.split(" ")[1]);

              if (taskNumber > taskCounter) { //task number is out of range
                throw new RiceException("Invalid task number"); 
              }

              list.get(taskNumber - 1).unmark();
              System.out.println("  OK, I've marked this task as not done yet:");
              System.out.println("    " + list.get(taskNumber - 1).toString());
              break;
          } 

          case todo -> {
            if (userInput.split(" ", 2).length < 2) { //desc is empty
              throw new RiceException("OOPS!!! The decription of todo cannot be empty");
            }
            Todo task = new Todo(userInput.split(" ", 2)[1]);
            list.add(task);
            taskCounter += 1;
            System.out.println("Got it. I've added this task:\n" + task.toString());
            System.out.println(String.format("Now you have %d tasks in the list", taskCounter));
            break;
          } 

          case deadline -> {

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
            list.add(deadline);
            taskCounter += 1;
            System.out.println("Got it. I've added this task:\n" + deadline.toString());
            System.out.println(String.format("Now you have %d tasks in the list", taskCounter));
            break;
          }
          case event -> {

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
            list.add(event);
            taskCounter += 1;
            System.out.println("Got it. I've added this task:\n" + event.toString());
            System.out.println(String.format("Now you have %d tasks in the list", taskCounter));
            break;
          }

            case delete -> {
              if(userInput.split(" ").length < 2) {
                throw new RiceException("OOPS!!! task number cannot be empty");
              }

              int taskNumber = Integer.parseInt(userInput.split(" ",2)[1]);
              if (taskNumber == 0 ||  taskNumber > taskCounter) {
                throw new RiceException("OOPS!! Task number cannot exceed number of tasks in list");
              }

              Task removedTask = list.remove(taskNumber - 1);
              taskCounter -= 1;
              System.out.println("Noted. I've removed this task:\n" + " " + removedTask.toString());
              System.out.println(String.format("Now you have %d tasks in the list.", taskCounter)); 
              break;
              }
            } 
            localList.save(list);
          }
        } catch (RiceException e){
          System.out.println(e.getMessage());
        }
    }
}
