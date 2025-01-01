package test.db.code;

import java.sql.*;

public class SkipLockedRowsExample {

    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Establish a database connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Start a new thread for the read lock with SKIP LOCKED
            Thread t1 = new Thread() {
                public void run() {
                    try {
                        // Each thread gets its own connection
                        Connection threadConnection = DriverManager.getConnection(URL, USER, PASSWORD);
                        performSkipLockedRead(threadConnection);  // Execute the read lock with SKIP LOCKED inside the thread
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

    // Method to perform Read Lock with SKIP LOCKED
    private static void performSkipLockedRead(Connection connection) throws SQLException {
        // Disable auto-commit for transaction control
        connection.setAutoCommit(false);

        // SQL query with "FOR UPDATE SKIP LOCKED" to skip locked rows
        String selectSQL = "SELECT * FROM employees WHERE department_id = 1 FOR UPDATE SKIP LOCKED";
        
        try (Statement stmt = connection.createStatement()) {
            // Execute the query with the read lock and skip locked rows
            ResultSet resultSet = stmt.executeQuery(selectSQL);
            while (resultSet.next()) {
                int empId = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                System.out.println("Read row (Skipped locked): " + empId + " - " + name);
            }

            // Commit the transaction
            connection.commit();
        }
    }
}
