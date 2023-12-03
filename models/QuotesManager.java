package com.qlilop.kurs.models;

import com.qlilop.kurs.DBNamings;
import com.qlilop.kurs.db.DatabaseConnector;

import java.sql.*;

public class QuotesManager {

    public static QuoteItem addQuoteToDB(QuoteItem item) {
        int genKey = -1;
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement statement = null;
            if (connection != null) {
                statement = getCreateStatement(item, connection);
            } else {
                System.out.println("Connection is null");
                return null;
            }
//            System.out.println(statement.toString());
//            ResultSet resultSet = statement.executeQuery();
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                genKey = generatedKeys.getInt(1);

            }
            connection.close();
            return new QuoteItem(
                    genKey,
                    item
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Boolean updateQuote(QuoteItem newQuote) {
        try {
            Connection connect = DatabaseConnector.getConnection();
            PreparedStatement statement = getUpdateStatement(newQuote,
                                                             connect
            );
            if (statement == null) {
                connect.close();
                return false;
            }
            statement.execute();
            connect.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean deleteQuote(Integer id) {
        try {
            Connection connect = DatabaseConnector.getConnection();
            PreparedStatement statement = preparedDeleteStatement(id, connect);
            if (statement == null) {
                connect.close();
                return false;
            }
            statement.execute();
            connect.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static PreparedStatement getCreateStatement(QuoteItem item, Connection connection) {
        StringBuilder sb = new StringBuilder().append("INSERT INTO ")
                .append(DBNamings.QUOTES_TABLE.TABLE_NAME)
                .append(" (")
                .append(DBNamings.QUOTES_TABLE.COLUMN_ID)
                .append(", ")
                .append(DBNamings.QUOTES_TABLE.DATE_COLUMN_NAME)
                .append(", ")
                .append(DBNamings.QUOTES_TABLE.TEXT_COLUMN_NAME)
                .append(", ")
                .append(DBNamings.QUOTES_TABLE.TEATCHER_COLUMN_NAME)
                .append(", ")
                .append(DBNamings.QUOTES_TABLE.AUTHOR_COLUMN_NAME)
                .append(", ")
                .append(DBNamings.QUOTES_TABLE.SUBJECT_COLUMN_NAME)
                .append(") VALUES (?, ?, ?, ?, ?, ?)");
        try {
            PreparedStatement statement =
                    connection
                            .prepareStatement(sb.toString(),
                                              Statement.RETURN_GENERATED_KEYS
                            );
            statement.setNull(1, Types.INTEGER);
            statement.setDate(2, item.getDate());
            statement.setString(3, item.getQuoteText());
            statement.setString(4, item.getTeacherName());
            statement.setInt(5, item.getAuthorId());
            statement.setString(6, item.getSubjectName());
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PreparedStatement preparedDeleteStatement(Integer id, Connection connection) {
        StringBuilder sb = new StringBuilder()
                .append("DELETE FROM ")
                .append(DBNamings.QUOTES_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(DBNamings.QUOTES_TABLE.COLUMN_ID)
                .append(" = ?");
        try {
            PreparedStatement statement = connection
                    .prepareStatement(
                    sb.toString()
            );
            statement.setInt(1, id);
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PreparedStatement getUpdateStatement(QuoteItem quote, Connection connection) {
        StringBuilder sb = new StringBuilder()
                .append("UPDATE ")
                .append(DBNamings.QUOTES_TABLE.TABLE_NAME)
                .append(" SET ")
                .append(DBNamings.QUOTES_TABLE.DATE_COLUMN_NAME)
                .append(" = ?, ")
                .append(DBNamings.QUOTES_TABLE.TEXT_COLUMN_NAME)
                .append(" = ?, ")
                .append(DBNamings.QUOTES_TABLE.TEATCHER_COLUMN_NAME)
                .append(" = ?, ")
                .append(DBNamings.QUOTES_TABLE.SUBJECT_COLUMN_NAME)
                .append(" = ? WHERE ")
                .append(DBNamings.QUOTES_TABLE.COLUMN_ID)
                .append(" = ?");
        try {

            PreparedStatement statement = connection.prepareStatement(sb.toString());
            statement.setDate(1, quote.getDate());
            statement.setString(2, quote.getQuoteText());
            statement.setString(3, quote.getTeacherName());
            statement.setString(4, quote.getSubjectName());
            statement.setInt(5, quote.getId());
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
