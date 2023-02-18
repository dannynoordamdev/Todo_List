import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        boolean exit = true;
        while (exit){
            System.out.println("Dear user, please select what you want to do!");
            System.out.println("Option 1: Add task");
            System.out.println("Option 2: Read existing task");
            System.out.println("Option 3: Exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            if (option == 1){
                System.out.println("Under which name can I add this task?");
                String name = scanner.nextLine();
                System.out.println("What is the task?");
                String task = scanner.nextLine();
                insertData(name,task);
                System.out.println("Upload successful!");
                System.out.println();

            } else if (option ==2) {
                System.out.println("For which name would you like to retrieve tasks?");
                String name = scanner.nextLine();
                getTasksForName(name);
                System.out.println();


            } else if (option == 3) {
                System.out.println("Application closed.");
                exit = false;
            }
            else {
                System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void getTasksForName(String name) {
        String url = "jdbc:mysql://localhost:3306/todo_list";
        String user = "root";
        String password = "root";
        try (Connection connection = DriverManager.getConnection(url, user, password);

             PreparedStatement statement = connection.prepareStatement("SELECT task FROM tasks WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                System.out.println(result.getString("task"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving tasks: " + e.getMessage());
        }
    }

    private static void insertData(String name, String task) {
        String url = "jdbc:mysql://localhost:3306/todo_list";
        String username = "root";
        String password = "Paris2023!";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO tasks (name, task) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, task);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }
}
