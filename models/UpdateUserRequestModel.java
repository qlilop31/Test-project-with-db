package com.qlilop.kurs.models;

import com.qlilop.kurs.DBNamings;
import com.qlilop.kurs.SerializableModelWithUserInput;
import com.qlilop.kurs.db.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UpdateUserRequestModel implements SerializableModelWithUserInput {
    private final Object changeValue;
    private final AnUserModel user;
    private final Integer id;
    private final UpdateUserEnum change;


    public <T> UpdateUserRequestModel(
            AnUserModel user,
            UpdateUserEnum change,
            T changeValue
    ) {
        this.user = user;
        this.id = user.id;
        this.change = change;
        this.changeValue = changeValue;
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            databaseConnector.requestUpdate(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PreparedStatement getPreparedStatement(Connection connection) {
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
            statement = connection.prepareStatement(query);
            statement.setString(
                    1,
                    changeValue.toString()
            );
            statement.setInt(2, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return statement;
    }

    @Override
    public void serialize(ResultSet resultSet) {
        try {
            resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
