package com.qlilop.kurs;

import com.qlilop.kurs.models.QuoteItem;
import com.qlilop.kurs.models.QuotesManager;
import com.qlilop.kurs.models.QuotesModel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Tests {
    @Test
    void testQuoteUpdate() {
        QuotesModel quotesModel = new QuotesModel();
        QuoteItem item = quotesModel.getQuoteById(1);
        String targetText;
        if (item.getQuoteText().equals("one")) {
            targetText = "two";
        } else {
            targetText = "one";
        }
        QuotesManager.updateQuote(
                new QuoteItem(
                        item.getId(),
                        item.getDateString(),
                        item.getDate(),
                        targetText,
                        item.getTeacherName(),
                        item.getSubjectName(),
                        item.getAuthorId()
                )
        );
        quotesModel.updateModelData();
        Assertions.assertEquals(
                targetText,
                quotesModel.getQuoteById(1).getQuoteText()
        );
    }
}
