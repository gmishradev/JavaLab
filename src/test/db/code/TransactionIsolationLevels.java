package test.db.code;

import java.sql.*;

public class TransactionIsolationLevels {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        // Demonstrate each isolation level
        try {
            // Test different isolation levels
            testIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED, "READ UNCOMMITTED");
            testIsolationLevel(Connection.TRANSACTION_READ_COMMITTED, "READ COMMITTED");
            testIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ, "REPEATABLE READ");
            testIsolationLevel(Connection.TRANSACTION_SERIALIZABLE, "SERIALIZABLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to test each isolation level
    private static void testIsolationLevel(int isolationLevel, String levelName) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            System.out.println("Testing Isolation Level: " + levelName);
            
            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Set the specified isolation level
            connection.setAutoCommit(false);  // Disable auto-commit for transaction control
            connection.setTransactionIsolation(isolationLevel);
            
            System.out.println("Isolation Level set to: " + connection.getTransactionIsolation());

            // Simulate a transaction (example: inserting and reading data)
            statement = connection.createStatement();
            String selectSQL = "SELECT * FROM employees WHERE department_id = 1";
            
            // Insert some data (this would normally be in a separate transaction)
            String insertSQL = "INSERT INTO employees (employee_id, name, department_id) VALUES (1001, 'John Doe', 1)";
            statement.executeUpdate(insertSQL);
            System.out.println("Inserted a new employee.");

            // Start a query within the transaction (this will be affected by isolation level)
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                System.out.println("Employee ID: " + resultSet.getInt("employee_id") + 
                                   ", Name: " + resultSet.getString("name"));
            }

            // Commit the transaction
            connection.commit();
            System.out.println("Transaction committed.\n");

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
                System.out.println("Transaction rolled back due to an error.");
            }
            throw e;
        } finally {
            // Clean up resources
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}
