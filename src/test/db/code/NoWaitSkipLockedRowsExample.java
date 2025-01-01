package test.db.code;

import java.sql.*;

public class NoWaitSkipLockedRowsExample {

    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Establish a database connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Start a new thread for the read lock with NO WAIT
            Thread t1 = new Thread() {
                public void run() {
                    try {
                        // Each thread gets its own connection
                        Connection threadConnection = DriverManager.getConnection(URL, USER, PASSWORD);
                        performNoWaitRead(threadConnection);  // Execute the read lock with NO WAIT inside the thread
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            };
            t1.start();  // Start the thread

            // Wait for the thread to finish
            t1.join();

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to perform Read Lock with NO WAIT
    private static void performNoWaitRead(Connection connection) throws SQLException {
        // Disable auto-commit for transaction control
        connection.setAutoCommit(false);

        // SQL query with "FOR UPDATE NO WAIT" to skip locked rows without waiting
        String selectSQL = "SELECT * FROM employees WHERE department_id = 1 FOR UPDATE NO WAIT";
        
        try (Statement stmt = connection.createStatement()) {
            // Execute the query with the read lock and no wait for locked rows
            ResultSet resultSet = stmt.executeQuery(selectSQL);
            if (!resultSet.next()) {
                System.out.println("No rows returned or all rows are locked.");
            } else {
                // Process the result set if rows are returned
                do {
                    int empId = resultSet.getInt("employee_id");
                    String name = resultSet.getString("name");
                    System.out.println("Read row (No Wait): " + empId + " - " + name);
                } while (resultSet.next());
            }

            // Commit the transaction
            connection.commit();
        } catch (SQLException e) {
            // Handle error if no rows are returned due to locks (could log or retry)
            System.out.println("Query failed or locked rows encountered: " + e.getMessage());
            connection.rollback();  // Optionally, rollback the transaction
        }
    }
}
