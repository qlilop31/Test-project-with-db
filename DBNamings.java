package com.qlilop.kurs;

public abstract class DBNamings {
    public static class USERS_TABLE {
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_ID = "id";
        public static final String LOGIN_COLUMN_NAME = "login";
        public static final String PASSWORD_COLUMN_NAME = "password_h";
        public static final String ROLE_COLUMN_NAME = "role_level";
    }
    public static class QUOTES_TABLE {
        public static final String TABLE_NAME = "Quotes";
        public static final String COLUMN_ID = "id";
        public static final String DATE_COLUMN_NAME = "foundation_date";
        public static final String TEXT_COLUMN_NAME = "quote_text";
        public static final String TEATCHER_COLUMN_NAME = "teacher";
        public static final String AUTHOR_COLUMN_NAME = "author_id";
        public static final String SUBJECT_COLUMN_NAME = "subject_name";
    }

}
