package factories;

import java.sql.*;

public class DataBaseFactory {

    private final String connectionURL = "jdbc:mysql://localhost:3306/sys?useSSL=false&Unicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "admin27";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DataBaseFactory() {
        try {
            connection = (Connection) DriverManager.getConnection(connectionURL, user, password);
            if (connection != null) {
                System.out.println("Connected succesfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfUserExists(String login) {
        String loginQuery = "SELECT 1 FROM Users WHERE login = '" + login + "';";
        try {
            return checkIfExists(loginQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPassword(String login, String password) {
        String doesPasswordMatchLoginQuery = "SELECT 1 FROM Users WHERE login LIKE '" + login + "' AND pass LIKE '" + password + "';";
        try {
            return checkIfExists(doesPasswordMatchLoginQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkIfExists(String query) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

}
