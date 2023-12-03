package com.qlilop.kurs.models;

import com.qlilop.kurs.DBNamings;

public enum UpdateUserEnum {
    LOGIN,
    PASSWORD,
    ROLE;

    public String getColumnName() {
        return switch (this) {
            case LOGIN -> DBNamings.USERS_TABLE.LOGIN_COLUMN_NAME;
            case PASSWORD -> DBNamings.USERS_TABLE.PASSWORD_COLUMN_NAME;
            case ROLE -> DBNamings.USERS_TABLE.ROLE_COLUMN_NAME;
        };
    }
}
