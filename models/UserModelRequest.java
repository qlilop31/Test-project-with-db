package com.qlilop.kurs.models;

import com.qlilop.kurs.DBNamings;
import com.qlilop.kurs.PasswordHelper;
import com.qlilop.kurs.SerializableModelWithUserInput;
import com.qlilop.kurs.db.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModelRequest implements SerializableModelWithUserInput {
    private String login = null;
    private String password = null;
    private Integer id;
    private AnUserModel user = null;

    public UserModelRequest(String login, String password) {
        this.login = login;
        this.password = password;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.getRequestWithUserData(this);
    }

    public UserModelRequest(Integer id) {
        this.id = id;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.getRequestWithUserData(this);
    }

    public AnUserModel getUser() {
        return user;
    }

    @Override
    public PreparedStatement getPreparedStatement(Connection connection) {
        if (id != null) {
            return statementByID(connection);
        } else {
            return statementByLogin(connection);
        }
    }

    private PreparedStatement statementByID(Connection connection) {
        String query = new StringBuilder()
                .append("SELECT ")
                .append("*")
                .append(" FROM ")
                .append(DBNamings.USERS_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(DBNamings.USERS_TABLE.COLUMN_ID)
                .append(" = (?);")
                .toString();
        PreparedStatement statement;
        try {
            statement = connection
                    .prepareStatement(query);
            statement.setInt(1, id);
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PreparedStatement statementByLogin(Connection connection) {
        String query = new StringBuilder()
                .append("SELECT ")
                .append("*")
                .append(" FROM ")
                .append(DBNamings.USERS_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(DBNamings.USERS_TABLE.LOGIN_COLUMN_NAME)
                .append(" = (?) and ")
                .append(DBNamings.USERS_TABLE.PASSWORD_COLUMN_NAME)
                .append(" = (?);")
                .toString();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(
                    2,
                    PasswordHelper.generateStorngPasswordHash(password)
            );
            return statement;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void serialize(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                user = new AnUserModel(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password_h"),
                        switch (resultSet.getString(DBNamings.USERS_TABLE.ROLE_COLUMN_NAME)) {
                            case "superUser" -> UserRole.superUser;
                            default -> UserRole.basic;
                        }
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
