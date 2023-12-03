package com.qlilop.kurs;

import com.qlilop.kurs.models.*;
import org.junit.jupiter.api.Assertions;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class Starter {
    public static void main(String[] args) {
//        genAndDeleteQuote();
//        try {
//            System.out.println(PasswordHelper.generateStorngPasswordHash("2"));
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        }


    }

    private static void genAndDeleteQuote() {
        QuoteItem q = generateQuote();
        QuotesManager.deleteQuote(q.getId());
    }

    private static QuoteItem generateQuote() {
        QuotesModel quotesModel = new QuotesModel();
        quotesModel.printQuotes();
        QuoteItem newQ = QuotesManager.addQuoteToDB(
                new QuoteItem(
                        -1,
                        "2022-01-01",
                        new Date(2022, 1, 1),
                        "Привет мир!",
                        "Андрей",
                        "subj",
                        1
                )
        );
        System.out.println(newQ.getId());
        return newQ;
    }

    private void testEditingOfQuotes() {
        QuotesModel quotesModel = new QuotesModel();
        QuoteItem item = quotesModel.getQuoteById(1);
        String targetText;
        if (item.getQuoteText().equals("one")) {
            targetText = "two";
        } else {
            targetText = "one";
        }
        QuotesManager.updateQuote(new QuoteItem(item.getId(),
                                                item.getDateString(),
                                                item.getDate(),
                                                targetText,
                                                item.getTeacherName(),
                                                item.getSubjectName(),
                                                item.getAuthorId()
        ));
        quotesModel.updateModelData();
        System.out.println(targetText);
        System.out.println(quotesModel.getQuoteById(1).getQuoteText());
    }
}
