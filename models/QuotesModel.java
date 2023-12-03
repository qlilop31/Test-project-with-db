package com.qlilop.kurs.models;

import com.qlilop.kurs.DBNamings;
import com.qlilop.kurs.SerializableModel;
import com.qlilop.kurs.db.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuotesModel extends ArrayList<QuoteItem> implements SerializableModel {

    public QuotesModel() {
        updateModelData();
    }

    public ArrayList<QuoteItem> getQuotes() {
        return this;
    }

    public void changeElement(QuoteItem old, QuoteItem newItem) {
        this.removeIf(item -> item.id == old.id);
        //add to index
        this.add(newItem);
    }

    public Boolean hasSameQuote(QuoteItem item) {
        for (QuoteItem quoteItem : this) {
            if (quoteItem.getQuoteText().equals(item.getQuoteText())
                    && quoteItem.getTeacherName().equals(item.getTeacherName())
                    && quoteItem.getSubjectName().equals(item.getSubjectName())
            ) {
                return true;
            }
        }
        return false;
    }

    public void updateModelData() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.getSQLAsk(this);
    }

    public void printQuotes() {
        for (QuoteItem quote : this) {
            System.out.println(quote);
        }
    }

    public QuoteItem getQuoteById(int id) {
        for (QuoteItem quote : this) {
            if (quote.getId() == id) {
                return quote;
            }
        }
        return null;
    }

    private ArrayList<QuoteItem> resultSetParser(ResultSet resultSet) {
        ArrayList<QuoteItem> quotes = new ArrayList<>();
        try {
            while (resultSet.next()) {
                try {
                    quotes.add(
                            new QuoteItem(
                                    resultSet.getInt(DBNamings.QUOTES_TABLE.COLUMN_ID),
                                    resultSet.getString(DBNamings.QUOTES_TABLE.DATE_COLUMN_NAME),
                                    resultSet.getDate(DBNamings.QUOTES_TABLE.DATE_COLUMN_NAME),
                                    resultSet.getString(DBNamings.QUOTES_TABLE.TEXT_COLUMN_NAME),
                                    resultSet.getString(DBNamings.QUOTES_TABLE.TEATCHER_COLUMN_NAME),
                                    resultSet.getString(DBNamings.QUOTES_TABLE.SUBJECT_COLUMN_NAME),
                                    resultSet.getInt(DBNamings.QUOTES_TABLE.AUTHOR_COLUMN_NAME)
                            )
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quotes;
    }

    @Override
    public void serialize(ResultSet resultSet) {
        ArrayList<QuoteItem> res = resultSetParser(resultSet);
        this.clear();
        this.addAll(res);
    }

    @Override
    public String getQuery() {
        return new StringBuilder().append("SELECT * FROM std_")
                .append(
                        DatabaseConnector.DBUSR
                )
                .append("_")
                .append(DatabaseConnector.DBNAME)
                .append(".Quotes")
                .toString();
    }
}
