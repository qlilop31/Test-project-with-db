package com.qlilop.kurs.models;

import com.qlilop.kurs.SerializableModelWithUserInput;
import com.qlilop.kurs.db.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AssignedUsers implements SerializableModelWithUserInput {
    private final int id;
    private final ArrayList<Integer> usersIDs = new ArrayList<>();
    private static final String TABLE_NAME = "Management";
    private static final String USERID_COLUMN_NAME = "user_id";
    private static final String CONTROLLED_COLUMN_NAME = "controlled_user";

    public AssignedUsers(Integer mainUserID) {
        this.id = mainUserID;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getUsersIDs() {
        return usersIDs;
    }

    public Boolean isUserInControled(Integer userID) {
        return usersIDs.contains(userID);
    }

    public void updateModelData() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.getRequestWithUserData(this);
    }

    public void printUsers() {
        for (Integer userID : usersIDs) {
            System.out.println(userID);
        }
    }

    @Override
    public void serialize(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                usersIDs.add(
                        resultSet.getInt(CONTROLLED_COLUMN_NAME)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PreparedStatement getPreparedStatement(Connection connection) {
        String query = new StringBuilder()
                .append("SELECT ")
                .append(CONTROLLED_COLUMN_NAME)
                .append(" FROM ")
                .append(TABLE_NAME)
                .append(" WHERE ")
                .append(USERID_COLUMN_NAME)
                .append(" = (?);")
                .toString();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(
                    1,
                    id
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return statement;
    }

}
