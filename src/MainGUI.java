import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MainGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("TODO List Application");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Dear user, please select what you want to do!");
        panel.add(label);

        JButton addButton = new JButton("Add task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Under which name can I add this task?");
                String task = JOptionPane.showInputDialog("What is the task?");
                try {
                    DatabaseConnector.insertData(name, task);
                    JOptionPane.showMessageDialog(null, "Upload successful!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error inserting data: " + ex.getMessage());
                }
            }
        });
        panel.add(addButton);

        JButton readButton = new JButton("Read existing task");
        readButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("For which name would you like to retrieve tasks?");
            try {
                List<String> tasks = DatabaseConnector.getTasksForName(name);
                String message;
                if (tasks.isEmpty()) {
                    message = "No tasks found for " + name;
                } else {
                    message = "Tasks for " + name + ":\n" + String.join("\n", tasks);
                }
                JOptionPane.showMessageDialog(null, message);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error retrieving tasks: " + ex.getMessage());
            }
        });
        panel.add(readButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}

class DatabaseConnector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/todo_list";

    static final String USER = "root";
    static final String PASS = "Paris2023!";

    private static java.sql.Connection connection;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            connection = java.sql.DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database: " + ex.getMessage());
            System.exit(0);
        }
    }

    static void insertData(String name, String task) throws SQLException {
        java.sql.PreparedStatement statement = connection.prepareStatement("INSERT INTO tasks (name, task) VALUES (?, ?)");
        statement.setString(1, name);
        statement.setString(2, task);
        statement.executeUpdate();
        statement.close();
    }

    static List<String> getTasksForName(String name) throws SQLException {
        java.sql.PreparedStatement statement = connection.prepareStatement("SELECT task FROM tasks WHERE name = ?");
        statement.setString(1, name);
        java.sql.ResultSet resultSet = statement.executeQuery();
        java.util.ArrayList<String> tasks = new java.util.ArrayList<>();
        while (resultSet.next()) {
            tasks.add(resultSet.getString("task"));
        }
        resultSet.close();
        statement.close();
        return tasks;
    }
}
