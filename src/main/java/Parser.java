package main.java;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    Scanner sc;
    TaskList taskList;

    public Parser(TaskList taskList) {
        this.sc = new Scanner(System.in);
        this.taskList = taskList;
    }

    public void handleCommand() {
        while (true) {
            String command = sc.next();
            switch (command) {
                case "bye":
                    handleBye();
                    return;
                case "list":
                    handleList();
                    break;
                case "done":
                    try {
                        handleDone();
                    } catch (DukeException e) {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("     " + e.getMessage());
                        System.out.println("    ____________________________________________________________\n");
                    }
                    break;
                case "delete":
                    try {
                        handleDelete();
                    } catch (DukeException e) {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("     " + e.getMessage());
                        System.out.println("    ____________________________________________________________\n");
                    }
                    break;
                case "todo":
                    try {
                        handleTodo();
                    } catch (DukeException e) {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("     " + e.getMessage());
                        System.out.println("    ____________________________________________________________\n");
                    }
                    break;
                case "deadline":
                    try {
                        handleDeadline();
                    } catch (DukeException e) {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("     " + e.getMessage());
                        System.out.println("    ____________________________________________________________\n");
                    }
                    break;
                case "event":
                    try {
                        handleEvent();
                    } catch (DukeException e) {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("     " + e.getMessage());
                        System.out.println("    ____________________________________________________________\n");
                    }
                    break;
                default:
                    try {
                        handleDefault();
                    } catch (DukeException e) {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("     " + e.getMessage());
                        System.out.println("    ____________________________________________________________\n");
                    }
            }
        }
    }

    public void handleBye() {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________\n");
        return;
    }

    public void handleList() {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.print("     " + (i + 1) + ".");
            taskList.get(i).printDescription();
        }
        System.out.println("    ____________________________________________________________\n");
    }

    public void handleDone() throws DukeException {
        String doneCommand = sc.nextLine();
        int index = 0;
        if (doneCommand.isEmpty()) {
            throw new DukeException("\u2639 OOPS!!! I need to know the index of the task to be done!");
        }
        try {
            index = Integer.parseInt(doneCommand.stripLeading());
        } catch (NumberFormatException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     \u2639 Please enter a valid integer!!");
            System.out.println("    ____________________________________________________________\n");
            return;
        }
        if (index > taskList.size()) {
            throw new DukeException("\u2639 Your number is too large!!");
        }
        Task currentTask = taskList.get(index - 1);
        currentTask.markAsDone();
        System.out.println("    ____________________________________________________________");
        System.out.println("     Nice! I've marked this task as done:");
        System.out.print("     ");
        currentTask.printDescription();
        System.out.println("    ____________________________________________________________\n");
    }

    public void handleDelete() throws DukeException {
        String deleteCommand = sc.nextLine();
        int index = 0;
        if (deleteCommand.isEmpty()) {
            throw new DukeException("\u2639 OOPS!!! I need to know the index of the task to be deleted!");
        }
        try {
            index = Integer.parseInt(deleteCommand.stripLeading());
        } catch (NumberFormatException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     \u2639 Please enter a valid integer!!");
            System.out.println("    ____________________________________________________________\n");
            return;
        }
        if (index > taskList.size()) {
            throw new DukeException("\u2639 Your number is too large!!");
        }
        Task currentTask = taskList.get(index - 1);
        taskList.remove(index - 1);
        System.out.println("    ____________________________________________________________");
        System.out.println("     Noted! I've removed this task:");
        System.out.print("     ");
        currentTask.printDescription();
        System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________\n");
    }

    public void handleTodo() throws DukeException {
        String todoDescription = sc.nextLine();
        if (todoDescription.isEmpty()) {
            throw new DukeException("\u2639 OOPS!!! The description of a todo cannot be empty.");
        }
        Todo todo = new Todo(todoDescription);
        taskList.add(todo);
        System.out.println("    ____________________________________________________________");
        System.out.println("     Got it. I've added this task:");
        System.out.print("       ");
        todo.printDescription();
        System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________\n");
    }

    public void handleDeadline() throws DukeException {

        String deadlineCommand = sc.nextLine();
        if (deadlineCommand.isEmpty()) {
            throw new DukeException("\u2639 OOPS!!! The description of a deadline cannot be empty.");
        }
        if (!deadlineCommand.contains("/by")) {
            throw new DukeException("\u2639 OOPS!!! The timing of a deadline cannot be empty.");
        }
        String[] strings = deadlineCommand.split("/by");
        String deadlineDescription = strings[0];
        String time = strings[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" yyyy-MM-dd");
        LocalDate date = LocalDate.parse(time, formatter);
        Deadline deadline = new Deadline(deadlineDescription, date);
        taskList.add(deadline);
        System.out.println("    ____________________________________________________________");
        System.out.println("     Got it. I've added this task:");
        System.out.print("        ");
        deadline.printDescription();
        System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________\n");
    }

    public void handleEvent() throws DukeException{
        String eventCommand = sc.nextLine();
        if (eventCommand.isEmpty()) {
            throw new DukeException("\u2639 OOPS!!! The description of an event cannot be empty.");
        }
        if (!eventCommand.contains("/at")) {
            throw new DukeException("\u2639 OOPS!!! The timing of an event cannot be empty.");
        }
        String[] strings = eventCommand.split("/at");
        String eventDescription = strings[0];
        String date = strings[1];
        Event event = new Event(eventDescription, date);
        taskList.add(event);
        System.out.println("    ____________________________________________________________");
        System.out.println("     Got it. I've added this task:");
        System.out.print("        ");
        event.printDescription();
        System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________\n");
    }

    public void handleDefault() throws DukeException {
        throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    public void updateFile() throws IOException {
        FileWriter fw = new FileWriter("./command.txt");
        FileWriter fwAppend = new FileWriter("./command.txt", true);
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String line;
            if(task instanceof Todo) {
                int done = task.isDone? 1:0;
                line = "T|" + done + "|" + task.description;
            } else if(task instanceof Deadline) {
                int done = task.isDone? 1:0;
                line = "D|" + done + "|" + task.description + "|" +
                        ((Deadline) task).deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else {
                int done = task.isDone? 1:0;
                line = "T|" + done + "|" + task.description + "|" + ((Event) task).time;
            }

            if (i == 0) {
                fw.write(line + '\n');
            } else if (i < taskList.size() - 1) {
                fwAppend.write(line + '\n');
            } else {
                fwAppend.write(line);
            }
        }
        fw.close();
        fwAppend.close();
    }
}