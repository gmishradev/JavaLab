package test.db.code;

import java.sql.*;

public class LockingExample {

    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        // Demonstrating read lock and write lock
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

            // Demonstrating Read Lock
            performReadLock(connection);
            // Demonstrating Write Lock
            System.out.println("\nDemonstrating Write Lock:");
            performWriteLock(connection);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources

            try {
                connection.rollback();
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to perform Read Lock (LOCK IN SHARE MODE)
    private static void performReadLock(Connection connection) throws SQLException {
        // Disable auto-commit for transaction control
        connection.setAutoCommit(false);

        // Read Lock using "LOCK IN SHARE MODE"
        String selectSQL = "SELECT * FROM employees WHERE department_id = 1 LOCK IN SHARE MODE";
        try (Statement stmt = connection.createStatement()) {
            // Execute the query with the read lock
            ResultSet resultSet = stmt.executeQuery(selectSQL);
            while (resultSet.next()) {
                int empId = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                System.out.println("Locked row (Read Lock): " + empId + " - " + name);
            }

            // Commit the transaction
            connection.commit();
        }
    }

    // Method to perform Write Lock (SELECT ... FOR UPDATE)
    private static void performWriteLock(Connection connection) throws SQLException {
        // Disable auto-commit for transaction control
        connection.setAutoCommit(false);

        // Write Lock using "SELECT ... FOR UPDATE"
        String selectSQL = "SELECT * FROM employees WHERE department_id = 1 FOR UPDATE";
        try (Statement stmt = connection.createStatement()) {
            // Execute the query with the write lock
            ResultSet resultSet = stmt.executeQuery(selectSQL);
            while (resultSet.next()) {
                int empId = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                System.out.println("Locked row (Write Lock): " + empId + " - " + name);
            }

            // Simulate some updates to the locked rows
            String updateSQL = "UPDATE employees SET salary = salary + 500 WHERE department_id = 1";
            stmt.executeUpdate(updateSQL);
            System.out.println("Updated salary for department 1.");

            // Commit the transaction
            connection.commit();
        }
    }
}
