public class Rice {
  private Parser parser = new Parser();
  private Storage storage = new Storage("data", "listOfTasks.txt");
  private Ui ui = new Ui();
  private TaskList tasks = new TaskList();

  public void run() {
    ui.greet();
    tasks.addAll(parser.parseTasks(storage.load()));
    while (true) {
      try {
        String input = ui.readInput().trim();
        if (input.equals("bye")) { ui.show("Bye. Hope to see you again soon!"); return; }
        String[] parts = input.split(" ", 2);
        switch (parts[0]) {
          case "list" -> showTasks();
          case "todo", "deadline", "event" -> addTask(input);
          case "mark", "unmark" -> changeStatus(parts[0], input);
          case "delete" -> deleteTask(input);
          default -> throw new RiceException("I'm sorry, but I dont know what that means :-(");
        }
      } catch (RiceException | IllegalArgumentException e) { ui.show(e.getMessage()); }
    }
  }

  private void showTasks() {
    for (int i = 0; i < tasks.size(); i++) {
      ui.show((i + 1) + "." + tasks.get(i));
    }
  }
  
  //add Task into taskList and parse into storage
  private void addTask(String input) throws RiceException { 
    Task task = parser.createTask(input);
    tasks.add(task);
    storage.save(tasks.getTasks());
    ui.show("Got it. I've added this task:");
    ui.show("  " + task);
    ui.show(String.format("Now you have %d tasks in the list", tasks.size()));
  }

  private void changeStatus(String command, String input) throws RiceException {
    int index = taskIndex(input); 
    if (command.equals("mark")) {
      tasks.mark(index); 
      ui.show("Nice! I've marked this task as done:");
      ui.show("  " + tasks.get(index));
    } else {
      tasks.unmark(index);
      ui.show("OK, I've marked this task as not done yet:");
      ui.show("  " + tasks.get(index));
    }
    storage.save(tasks.getTasks());
  }

  private void deleteTask(String input) throws RiceException { 
    Task removed = tasks.remove(taskIndex(input)); 
    ui.show("Removed: " + removed); storage.save(tasks.getTasks()); 
  }

  private int taskIndex(String input) throws RiceException {
    String[] p = input.split(" ", 2); 
    if (p.length < 2) {
      throw new RiceException("Task number is required");
    }

    try { 
      int n = Integer.parseInt(p[1].trim()); 
      if (n < 1 || n > tasks.size()) {
        throw new RiceException("Invalid task number"); 
        }
        return n -1;
      } catch (NumberFormatException e) { 
          throw new RiceException("Task number must be a number"); 
        }

  }

  public static void main(String[] args) { 
    new Rice().run(); 
  }

}
