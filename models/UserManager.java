package com.qlilop.kurs.models;

import com.qlilop.kurs.DBNamings;
import com.qlilop.kurs.PasswordHelper;
import com.qlilop.kurs.db.DatabaseConnector;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

import static com.qlilop.kurs.models.UpdateUserEnum.LOGIN;

public class UserManager {
    private AnUserModel user;

    public UserManager() {

    }

    public UserManager(String login, String password) {
        user = new UserModelRequest(login, password)
                .getUser();
    }

    public void updateUserData() {
        user = new UserModelRequest(user.id)
                .getUser();
    }

    public AnUserModel getUser() {
        return user;
    }

    public String setupNewField(
            AnUserModel user,
            UpdateUserEnum change,
            String changeValue
    ) {
        String query = new StringBuilder()
                .append("UPDATE ")
                .append(DBNamings.USERS_TABLE.TABLE_NAME)
                .append(" SET ")
                .append(
                        change.getColumnName()
                )
                .append(" = ? ")
                .append(" WHERE ")
                .append(
                        DBNamings.USERS_TABLE.COLUMN_ID
                )
                .append(" = ?;")
                .toString();
        System.out.println(query);
        PreparedStatement statement;
        try {
            Connection connection = DatabaseConnector.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(
                    1,
                    changeValue
            );
            statement.setInt(2, user.id);
            statement.execute();
            connection.close();
            System.out.println(change + " " + changeValue);
            switch (change) {
                case LOGIN -> this.user = new AnUserModel(
                        user.id,
                        changeValue,
                        user.password,
                        user.role
                );
                case PASSWORD -> this.user = new AnUserModel(
                        user.id,
                        user.login,
                        changeValue,
                        user.role
                );
                case ROLE -> this.user = new AnUserModel(
                        user.id,
                        user.login,
                        user.password,
                        UserRole.valueOf(changeValue)
                );
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            return "Ошибка: Пользователь с таким логином уже существует";
        }
        catch (SQLException e) {
            e.printStackTrace();
            return "Ошибка: Неизвестная ошибка";
        }
        return null;
    }

    public String registerUser(String login, String password) {
        String query = new StringBuilder().append("INSERT INTO ")
                .append(DBNamings.USERS_TABLE.TABLE_NAME)
                .append(" (")
                .append(DBNamings.USERS_TABLE.COLUMN_ID)
                .append(", ")
                .append(DBNamings.USERS_TABLE.LOGIN_COLUMN_NAME)
                .append(", ")
                .append(DBNamings.USERS_TABLE.PASSWORD_COLUMN_NAME)
                .append(", ")
                .append(DBNamings.USERS_TABLE.ROLE_COLUMN_NAME)
                .append(") VALUES (?, ?, ?, ?)").toString();
        System.out.println(query);
        PreparedStatement statement;
        try {
            Connection connection = DatabaseConnector.getConnection();
            if (connection != null) {
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            } else {
                return "Ошибка: Не удалось подключиться к базе данных";
            }
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, login);
            String hash;
            try {
                hash = PasswordHelper.generateStorngPasswordHash(password);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                return "Ошибка: Произошла ошибка при создании пароля";
            }
            statement.setString(3, hash);
            statement.setString(4, UserRole.basic.toString());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int genKey = -1;
            if (generatedKeys.next()) {
                genKey = generatedKeys.getInt(1);
            }
            connection.close();
            this.user = new AnUserModel(
                    genKey,
                    login,
                    hash,
                    UserRole.basic
            );
        } catch (SQLIntegrityConstraintViolationException e) {
            return "Ошибка: Пользователь с таким логином уже существует";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Ошибка: Неизвестная ошибка";
        }
        return null;
    }
}
