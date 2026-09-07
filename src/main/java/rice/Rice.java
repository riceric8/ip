package rice;

import java.util.List;

/**
 * Runs the Rice task manager application.
 */
public class Rice {
    private Parser parser = new Parser();
    private Storage storage = new Storage("data", "listOfTasks.txt");
    private Ui ui = new Ui();
    private TaskList tasks = new TaskList();
    private boolean hasLoadedSavedTasks;

    /**
     * Starts the read-evaluate loop for user commands.
     * Maintains the CLI version for RiceBot
     */
    public void run() {
        ui.greet();
        loadSavedTasks();
        while (true) {
            try {
                String input = ui.readInput().trim();
                if (input.equals("bye")) {
                    ui.show("Bye. Hope to see you again soon!");
                    return;
                }
                String[] parts = input.split(" ", 2);
                switch (parts[0]) {
                    case "list" -> ui.show(showTasks());
                    case "find" -> ui.show(findTasks(input));
                    case "todo", "deadline", "event" -> ui.show(addTask(input));
                    case "mark", "unmark" -> ui.show(changeStatus(parts[0], input));
                    case "delete" -> ui.show(deleteTask(input));
                    default -> throw new RiceException("I'm sorry, but I dont know what that means :-(");
                }
            } catch (RiceException | IllegalArgumentException e) {
                ui.show(e.getMessage());
            }
        }
    }

    private void loadSavedTasks() {
        if (!hasLoadedSavedTasks) {
            tasks.addAll(parser.parseTasks(storage.load()));
            hasLoadedSavedTasks = true;
        }
    }

    private String showTasks() {
        StringBuilder response = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            response.append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return response.toString().trim();
    }

    private String findTasks(String input) throws RiceException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new RiceException("Please provide a keyword to search for");
        }
        List<Task> matchingTasks = tasks.find(parts[1].trim());
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found.";
        }

        StringBuilder response = new StringBuilder();
        for (Task task : matchingTasks) {
            response.append(task).append("\n");
        }
        return response.toString().trim();
    }

    private String addTask(String input) throws RiceException {
        Task task = parser.createTask(input);
        tasks.add(task);
        storage.save(tasks.getTasks());
        return "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + String.format("Now you have %d tasks in the list", tasks.size());
    }

    private String changeStatus(String command, String input) throws RiceException {
        assert command.equals("mark") || command.equals("unmark");
        int index = taskIndex(input);
        if (command.equals("mark")) {
            tasks.mark(index);
            storage.save(tasks.getTasks());
            return "Nice! I've marked this task as done:\n"
                    + "  " + tasks.get(index);
        } else {
            tasks.unmark(index);
            storage.save(tasks.getTasks());
            return "OK, I've marked this task as not done yet:\n"
                    + "  " + tasks.get(index);
        }
    }

    private String deleteTask(String input) throws RiceException {
        Task removed = tasks.remove(taskIndex(input));
        storage.save(tasks.getTasks());
        return "Removed: " + removed;
    }

    private int taskIndex(String input) throws RiceException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            throw new RiceException("Task number is required");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1].trim());
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new RiceException("Invalid task number");
            }
            int index = taskNumber - 1;
            assert index > 0 && index < tasks.size();
            return index;
        } catch (NumberFormatException e) {
            throw new RiceException("Task number must be a number");
        }
    }

    public String displayList() {
        String list = "";
        for (int i = 0; i < tasks.size(); i += 1) {
            list += tasks.get(i).toString() + "\n";
        }
        return list;
    }

    public String getResponse(String input) {
        loadSavedTasks();
        try {
            String trimmedInput = input.trim();
            if (trimmedInput.equals("bye")) {
                return "Bye. Hope to see you again soon!";
            }
            String[] parts = trimmedInput.split(" ", 2);
            return switch (parts[0]) {
                case "list" -> showTasks();
                case "find" -> findTasks(trimmedInput);
                case "todo", "deadline", "event" -> addTask(trimmedInput);
                case "mark", "unmark" -> changeStatus(parts[0], trimmedInput);
                case "delete" -> deleteTask(trimmedInput);
                default -> throw new RiceException("I'm sorry, but I dont know what that means :-(");
            };
        } catch (RiceException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public static void main(String[] args) {
        new Rice().run();
    }
}
