package factories;

import converters.ArrayConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseFactory {

    private final String connectionURL = "jdbc:mysql://localhost:3306/sys";
    private String connectionParameters = "?useSSL=false&Unicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public boolean connect(String user, String password) {
        try {
            connection = DriverManager.getConnection(connectionURL.concat(connectionParameters), user, password);
            if (connection != null) {
                System.out.println("Connected succesfully");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[] getTablesNames() {
        String query = "SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA LIKE 'sys' AND TABLE_TYPE NOT LIKE 'VIEW';";
        return getFirstColumnValues(query);
    }

    public String[] getViewsNames() {
        String query = "SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA LIKE 'sys' AND TABLE_TYPE LIKE 'VIEW';";
        return getFirstColumnValues(query);
    }

    public String[] getTableColumnsNames(String tableName) {
        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'sys' AND TABLE_NAME = '" + tableName + "';";
        return getFirstColumnValues(query);
    }

    public Object[][] getTableData(String tableNme, String[] attributesToGet) {
        if (attributesToGet.length == 0) {
            return null;
        }
        String attributesToGetSeparatedByComas = "";
        for (String attribute : attributesToGet) {
            attributesToGetSeparatedByComas = attributesToGetSeparatedByComas.concat(attribute).concat(", ");
        }
        attributesToGetSeparatedByComas = attributesToGetSeparatedByComas.substring(0, attributesToGetSeparatedByComas.length() - 2);
        String query = "SELECT ".concat(attributesToGetSeparatedByComas).concat(" FROM ").concat(tableNme).concat(";");
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Object[] tmp = new Object[attributesToGet.length];
                for (int i = 0; i < attributesToGet.length; i++) {
                    tmp[i] = resultSet.getString(attributesToGet[i]);
                }
                list.add(tmp);
            }
            return ArrayConverter.listOfArraysToTwoDimArray(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String[] getFirstColumnValues(String query) {
        List<String> list = new ArrayList<String>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ArrayConverter.listToArray(list);
    }

}
