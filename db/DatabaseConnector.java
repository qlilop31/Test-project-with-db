package com.qlilop.kurs.db;

import com.qlilop.kurs.SecretBase;
import com.qlilop.kurs.SerializableModel;
import com.qlilop.kurs.SerializableModelWithUserInput;

import java.sql.*;

public class DatabaseConnector {
    public static final int DBUSR = 2013;
    public static final String DBNAME = "kurs";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    new StringBuilder()
                            .append("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_")
                            .append(DBUSR)
                            .append("_")
                            .append(DBNAME)
                            .toString(),
                    new StringBuilder()
                            .append("std_")
                            .append(DBUSR)
                            .append("_")
                            .append(DBNAME)
                            .toString(),
                    SecretBase
                            .DBPASSWORD
            );
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SerializableModel getSQLAsk(SerializableModel model) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(model.getQuery());
            model.serialize(resultSet);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public SerializableModelWithUserInput getRequestWithUserData(SerializableModelWithUserInput model) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = model.getPreparedStatement(connection);
//            System.out.println(statement.toString());
            ResultSet resultSet = statement.executeQuery();
            model.serialize(resultSet);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public SerializableModelWithUserInput requestUpdate(SerializableModelWithUserInput model) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = model.getPreparedStatement(connection);
            System.out.println(statement.toString());
            statement.execute();
            connection.close();
        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException(e);
        }
        return model;
    }
}
//постановка задачи
//проектирование (показать структуру) и реализация, структура решения, диаграммы, даталогическая модель
//реализация обобщенно и в местах детализировать в отдельных
//    "основные сценарии работы в приложении", продемонстрировать их в применении
//Пользователь хочет того, того и его путь пользователя.