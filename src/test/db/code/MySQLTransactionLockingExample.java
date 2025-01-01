package test.db.code;

import java.sql.*;

public class MySQLTransactionLockingExample {

    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        //    connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);


            // Turn off auto-commit to manage the transaction manually
            connection.setAutoCommit(false);

            // Table-level Lock
            statement = connection.createStatement();
            System.out.println("Acquiring table-level lock...");
            statement.execute("LOCK TABLES employees WRITE");  // Lock the entire 'employees' table for write

            // Perform table-level operations (e.g., update)
            String updateSQL = "UPDATE employees SET salary = salary + 1000 WHERE department_id = 1";
            statement.executeUpdate(updateSQL);
            System.out.println("Table-level update operation completed.");

            // Row-level Lock (using SELECT FOR UPDATE)
            String rowSelectSQL = "SELECT * FROM employees WHERE department_id = 1 FOR UPDATE";  // Lock rows where department_id = 1
            resultSet = statement.executeQuery(rowSelectSQL);
            while (resultSet.next()) {
                int empId = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                System.out.println("Locked row: " + empId + " - " + name);
                // Perform row-level operations here (if needed)
            }

            // Commit the transaction after all operations are completed
            connection.commit();
            System.out.println("Transaction committed.");

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    // Rollback in case of error
                    System.out.println("Rolling back transaction.");
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
