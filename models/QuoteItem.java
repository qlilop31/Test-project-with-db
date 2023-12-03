package com.qlilop.kurs.models;

import java.sql.Date;

public class QuoteItem {
    int id;
    Date date;
    String dateString;
    String quoteText;
    String teacherName;
    //    String authorLogin;
    String subjectName;
    int authorId;

    public QuoteItem(
            int id,
            String dateString,
            Date date,
            String quoteText,
            String teacherName,
            String subjectName,
            int authorId
    ) {
        this.id = id;
        this.dateString = dateString;
        this.date = date;
        this.quoteText = quoteText;
        this.teacherName = teacherName;
        this.subjectName = subjectName;
        this.authorId = authorId;
    }

    public QuoteItem(
            int id,
            QuoteItem quoteItem
    ) {
        this.id = id;
        this.date = quoteItem.date;
        this.dateString = quoteItem.dateString;
        this.quoteText = quoteItem.quoteText;
        this.teacherName = quoteItem.teacherName;
        this.subjectName = quoteItem.subjectName;
        this.authorId = quoteItem.authorId;
    }
    public QuoteItem(
            int id,
            Date date,
            String quoteText,
            String teacherName,
            String subjectName,
            int authorId
    ) {
        this.id = id;
        this.date = date;
        this.quoteText = quoteText;
        this.teacherName = teacherName;
        this.subjectName = subjectName;
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "QuoteItem{" +
                "id=" + id +
                ", date=" + date +
                ", dateString='" + dateString + '\'' +
                ", quoteText='" + quoteText + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", authorId=" + authorId +
                '}';
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return dateString;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getAuthorId() {
        return authorId;
    }
}
